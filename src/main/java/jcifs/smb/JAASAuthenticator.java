/*
 * © 2016 AgNO3 Gmbh & Co. KG
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package jcifs.smb;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import jcifs.CIFSContext;


/**
 * JAAS kerberos authenticator
 * 
 * Either configure JAAS for credential caching or reuse a single instance of this authenticator -otherwise you won't
 * get proper ticket caching.
 * 
 * Be advised that short/NetBIOS name usage is not supported with this authenticator. Always specify full FQDNs/Realm.
 * This can be a problem if using DFS in it's default configuration as that still returns referrals in short form.
 * See {@href https://support.microsoft.com/en-us/kb/244380}.
 * 
 * @author mbechler
 */
public class JAASAuthenticator extends Kerb5Authenticator implements CallbackHandler {

    private static final Logger log = Logger.getLogger(JAASAuthenticator.class);

    /**
     * 
     */
    private static final long serialVersionUID = -1648420815038372844L;

    private String serviceName;
    private Subject cachedSubject;
    private Configuration configuration;


    /**
     * Create an authenticator using the JAAS service <tt>jcifs</tt>
     * 
     * This will require that a keytab is configured in this service.
     * 
     * @param tc
     * 
     */
    public JAASAuthenticator ( CIFSContext tc ) {
        this(tc, "jcifs");
    }


    /**
     * Create an authenticator using the given JAAS service
     * 
     * This will require that a keytab is configured in this service.
     * 
     * @param tc
     *            context to use
     * @param serviceName
     *            JAAS configuration name
     */
    public JAASAuthenticator ( CIFSContext tc, String serviceName ) {
        super(tc, null);
        this.serviceName = serviceName;
    }


    /**
     * Create an authenticator using the given JAAS service and the specified credentials
     * 
     * @param tc
     *            context to use
     * @param serviceName
     *            JAAS configuration name
     * @param domain
     * @param username
     * @param password
     */
    public JAASAuthenticator ( CIFSContext tc, String serviceName, String domain, String username, String password ) {
        super(tc, null, domain, username, password);
        this.serviceName = serviceName;
    }


    /**
     * Create an authenticator using the given credentials
     * 
     * This will create a JAAS configuration that is used to obtain a TGT.
     * 
     * @param tc
     *            context to use
     * @param domain
     * @param username
     * @param password
     */
    public JAASAuthenticator ( CIFSContext tc, String domain, String username, String password ) {
        this(tc, new HashMap<String, String>(), domain, username, password);

    }


    /**
     * Create an authenticator using the given credentials
     * 
     * This will create a JAAS configuration with the specified properties that is used to obtain a TGT.
     * 
     * @param tc
     *            context to use
     * @param properties
     *            JAAS properties to set
     * @param domain
     * @param username
     * @param password
     */
    public JAASAuthenticator ( CIFSContext tc, Map<String, ?> properties, String domain, String username, String password ) {
        super(tc, null, domain, username, password);
        this.serviceName = "static";
        this.configuration = new StaticJAASConfiguration(properties);
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.smb.Kerb5Authenticator#isAnonymous()
     */
    @Override
    public boolean isAnonymous () {
        return false;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.smb.NtlmPasswordAuthentication#isGuest()
     */
    @Override
    public boolean isGuest () {
        return false;
    }


    @Override
    public Kerb5Authenticator clone () {
        JAASAuthenticator auth = new JAASAuthenticator(this.getContext());
        cloneInternal(auth, this);
        return auth;
    }


    /**
     * Clone the context
     * 
     * @param to
     * @param from
     */
    protected static void cloneInternal ( JAASAuthenticator to, JAASAuthenticator from ) {
        Kerb5Authenticator.cloneInternal(to, from);
        to.serviceName = from.serviceName;
        to.configuration = from.configuration;
        to.cachedSubject = from.cachedSubject;
    }


    /**
     * {@inheritDoc}
     *
     * @see jcifs.smb.Kerb5Authenticator#getSubject()
     */
    @Override
    public synchronized Subject getSubject () {
        if ( this.cachedSubject != null ) {
            return this.cachedSubject;
        }

        try {
            log.debug("Logging on");
            LoginContext lc;

            if ( this.configuration != null ) {
                lc = new LoginContext(this.serviceName, super.getSubject(), this, this.configuration);
            }
            else {
                lc = new LoginContext(this.serviceName, super.getSubject(), this);
            }
            lc.login();

            Subject s = lc.getSubject();
            if ( log.isDebugEnabled() ) {
                log.debug("Got subject " + s);
            }

            this.cachedSubject = s;
            return this.cachedSubject;
        }
        catch ( LoginException e ) {
            log.error("Failed to create login context", e);
            this.cachedSubject = new Subject();
            return null;
        }
    }


    /**
     * {@inheritDoc}
     *
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    @Override
    public void handle ( Callback[] callbacks ) throws IOException, UnsupportedCallbackException {
        for ( Callback cb : callbacks ) {
            if ( log.isDebugEnabled() ) {
                log.debug("Got callback " + cb.getClass().getName());
            }

            if ( cb instanceof NameCallback ) {
                NameCallback nc = (NameCallback) cb;
                String userDomain = this.getSpecifiedUserDomain();
                if ( this.getUsername() != null && userDomain != null ) {
                    nc.setName(this.getUsername() + "@" + userDomain);
                }
            }
            else if ( cb instanceof PasswordCallback ) {
                PasswordCallback pc = (PasswordCallback) cb;
                if ( this.getPassword() != null ) {
                    pc.setPassword(this.getPassword().toCharArray());
                }
            }
        }
    }
}