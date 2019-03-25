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
package jcifs.tests;


import jcifs.pac.PACDecodingException;
import jcifs.pac.PacMac;
import jcifs.util.Hexdump;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.kerberos.KerberosKey;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;


/**
 * @author mbechler
 *
 */
@SuppressWarnings ( {
    "nls", "javadoc", "restriction"
} )
public class PACTest {

    @Test
    public void testNFold () {
        // rfc3961 test vectors
        verifyNfold(64, "012345", "be072631276b1955");
        verifyNfold(56, "password", "78a07b6caf85fa");
        verifyNfold(64, "Rough Consensus, and Running Code", "bb6ed30870b7f0e0");
        verifyNfold(168, "password", "59e4a8ca7c0385c3c37b3f6d2000247cb6e6bd5b3e");
        verifyNfold(192, "MASSACHVSETTS INSTITVTE OF TECHNOLOGY", "db3b0d8f0b061e603282b308a50841229ad798fab9540c1b");
        verifyNfold(168, "Q", "518a54a215a8452a518a54a215a8452a518a54a215");
        verifyNfold(168, "ba", "fb25d531ae8974499f52fd92ea9857c4ba24cf297e");

        verifyNfold(64, "kerberos", "6b65726265726f73");
        verifyNfold(128, "kerberos", "6b65726265726f737b9b5b2b93132b93");
        verifyNfold(168, "kerberos", "8372c236344e5f1550cd0747e15d62ca7a5a3bcea4");
        verifyNfold(256, "kerberos", "6b65726265726f737b9b5b2b93132b935c9bdcdad95c9899c4cae4dee6d6cae4");
    }


    @Test
    public void testJavaHMAC () throws GeneralSecurityException {
        testJavaHMAC("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b", "Hi There", "9294727a3638bb1c13f48ef8158bfc9d");
        testJavaHMAC("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c", "Test With Truncation", "56461ef2342edc00f9bab995690efd4c");
        testJavaHMAC(
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            Hex.decode("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"),
            "56be34521d144c88dbb8c733f0e8b3f6");
    }


    private static void testJavaHMAC ( String key, String data, String expect ) throws GeneralSecurityException {
        testJavaHMAC(key, data.getBytes(StandardCharsets.US_ASCII), expect);
    }


    private static void testJavaHMAC ( String key, byte[] bytes, String expect ) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac m = Mac.getInstance("HmacMD5");
        m.init(new SecretKeySpec(Hex.decode(key), "HMAC"));
        byte[] mac = m.doFinal(bytes);
        checkBytes(Hex.decode(expect), mac);
    }


    @Test
    public void testRC4Checksum1 () throws PACDecodingException, GeneralSecurityException {
        String data = "fifteen sixteen";
        String key = "F7D3A155AF5E238A0B7A871A96BA2AB2";
        String expect = "6F65117E732E724D60FC2A9744CEAE43";
        testRC4HMac(5, data, key, expect);
    }


    @Test
    public void testRC4Checksum2 () throws PACDecodingException, GeneralSecurityException {
        String data = "seventeen eighteen nineteen twenty";
        String key = "F7D3A155AF5E238A0B7A871A96BA2AB2";
        String expect = "EB38CC97E2230F59DA4117DC5859D7EC";
        testRC4HMac(6, data, key, expect);
    }


    @Test
    public void testRC4Checksum3 () throws PACDecodingException, GeneralSecurityException {
        String data = "fifteen";
        String key = "F7D3A155AF5E238A0B7A871A96BA2AB2";
        String expect = "5BAE8D72EA64CA68189A85A5C8D80425";
        testRC4HMac(5, data, key, expect);
    }


    @Test
    public void testRC4Checksum4 () throws PACDecodingException, GeneralSecurityException {
        String data = "seventeen eighteen nineteen twenty twenty-one";
        String key = "F7D3A155AF5E238A0B7A871A96BA2AB2";
        String expect = "922A79152EF1D23032B17D8E023E8EBD";
        testRC4HMac(6, data, key, expect);
    }


    @Test
    public void testRC4Checksum5 () throws PACDecodingException, GeneralSecurityException {
        String data = "";
        String key = "F7D3A155AF5E238A0B7A871A96BA2AB2";
        String expect = "9121D44B1AD560C7A3152B3CAC453AB4";
        testRC4HMac(5, data, key, expect);
    }


    /**
     * @param data
     * @param key
     * @param expect
     * @throws GeneralSecurityException
     * @throws PACDecodingException
     */
    private static void testRC4HMac ( int usage, String data, String key, String expect ) throws GeneralSecurityException, PACDecodingException {
        byte[] keyb = Hex.decode(key);
        byte[] datab = data.getBytes(StandardCharsets.US_ASCII);
        byte[] mac = PacMac.calculateMacArcfourHMACMD5(usage, makeKey(keyb, 23), datab);
        checkBytes(Hex.decode(expect), mac);
    }

    /**
     * @param keybytes
     * @return
     */
    private static KerberosKey makeKey ( byte[] keybytes, int etype ) {
        return new KerberosKey(null, keybytes, etype, 0);
    }


    private static void checkBytes ( byte[] expect, byte[] have ) {
        if ( !Arrays.equals(expect, have) ) {
            Assert.fail(String.format("Expect: %s Have: %s", Hexdump.toHexString(expect), Hexdump.toHexString(have)));
        }
    }


    private static void verifyNfold ( int n, String string, String expect ) {
        byte[] expanded = PacMac.expandNFold(string.getBytes(StandardCharsets.US_ASCII), n / 8);
        Assert.assertEquals(expect, Hexdump.toHexString(expanded).toLowerCase(Locale.ROOT));
    }
}
