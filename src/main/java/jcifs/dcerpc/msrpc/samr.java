package jcifs.dcerpc.msrpc;


import jcifs.dcerpc.DcerpcMessage;
import jcifs.dcerpc.ndr.NdrBuffer;
import jcifs.dcerpc.ndr.NdrException;
import jcifs.dcerpc.ndr.NdrObject;
import jcifs.dcerpc.rpc;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;


@Generated ( "midlc" )
@SuppressWarnings ( "all" )
public class samr {

    public static String getSyntax () {
        return "12345778-1234-abcd-ef00-0123456789ac:1.0";
    }

    public static final int ACB_DISABLED = 1;
    public static final int ACB_HOMDIRREQ = 2;
    public static final int ACB_PWNOTREQ = 4;
    public static final int ACB_TEMPDUP = 8;
    public static final int ACB_NORMAL = 16;
    public static final int ACB_MNS = 32;
    public static final int ACB_DOMTRUST = 64;
    public static final int ACB_WSTRUST = 128;
    public static final int ACB_SVRTRUST = 256;
    public static final int ACB_PWNOEXP = 512;
    public static final int ACB_AUTOLOCK = 1024;
    public static final int ACB_ENC_TXT_PWD_ALLOWED = 2048;
    public static final int ACB_SMARTCARD_REQUIRED = 4096;
    public static final int ACB_TRUSTED_FOR_DELEGATION = 8192;
    public static final int ACB_NOT_DELEGATED = 16384;
    public static final int ACB_USE_DES_KEY_ONLY = 32768;
    public static final int ACB_DONT_REQUIRE_PREAUTH = 65536;

    public static final int SamrServerAccessReadMask = 983103;

    public static class SamrDomainAccessValues
    {
        public static final int DOMAIN_READ_PASSWORD_PARAMETERS = 0x00000001;
        public static final int DOMAIN_WRITE_PASSWORD_PARAMS = 0x00000002;
        public static final int DOMAIN_READ_OTHER_PARAMETERS = 0x00000004;
        public static final int DOMAIN_WRITE_OTHER_PARAMETERS = 0x00000008;
        public static final int DOMAIN_CREATE_USER = 0x00000010;
        public static final int DOMAIN_CREATE_GROUP = 0x00000020;
        public static final int DOMAIN_CREATE_ALIAS = 0x00000040;
        public static final int DOMAIN_GET_ALIAS_MEMBERSHIP = 0x00000080;
        public static final int DOMAIN_LIST_ACCOUNTS = 0x00000100;
        public static final int DOMAIN_LOOKUP = 0x00000200;
        public static final int DOMAIN_ADMINISTER_SERVER = 0x00000400;
        public static final int DOMAIN_ALL_ACCESS = 0x000F07FF;
        public static final int DOMAIN_READ = 0x00020084;
        public static final int DOMAIN_WRITE = 0x0002047A;
        public static final int DOMAIN_EXECUTE = 0x00020301;
        public static final int DOMAIN_GENERIC_READ_PERMISSIONS = 0x02000000;
        public static final int DOMAIN_ALL_READ_PERMISSIONS = DOMAIN_READ_PASSWORD_PARAMETERS
                                                                | DOMAIN_READ
                                                                | DOMAIN_GET_ALIAS_MEMBERSHIP
                                                                | DOMAIN_LIST_ACCOUNTS
                                                                | DOMAIN_LOOKUP
                                                                | DOMAIN_READ_OTHER_PARAMETERS
                                                                | DOMAIN_GENERIC_READ_PERMISSIONS;
    }

    public static class SamrAliasAccessValues
    {
        public static final int ALIAS_LIST_MEMBERS = 0x00000004;
        public static final int ALIAS_READ_INFORMATION = 0x00000008;
        public static final int ALIAS_READ = 0x00020004;
        public static final int ALIAS_ALL_READ_PERMISSIONS = ALIAS_LIST_MEMBERS
                                                            | ALIAS_READ_INFORMATION
                                                            | ALIAS_READ;
    }

    public static class SamrUserAccessValues
    {
        public static final int USER_READ_GENERAL = 0x00000001;
        public static final int USER_READ_PREFERENCES = 0x00000002;
        public static final int USER_WRITE_PREFERENCES = 0x00000004;
        public static final int USER_READ_LOGON = 0x00000008;
        public static final int USER_READ_ACCOUNT = 0x00000010;
        public static final int USER_WRITE_ACCOUNT = 0x00000020;
        public static final int USER_CHANGE_PASSWORD = 0x00000040;
        public static final int USER_FORCE_PASSWORD_CHANGE = 0x00000080;
        public static final int USER_LIST_GROUPS = 0x00000100;
        public static final int USER_READ_GROUP_INFORMATION = 0x00000200;
        public static final int USER_WRITE_GROUP_INFORMATION = 0x00000400;
        public static final int USER_ALL_ACCESS = 0x000F07FF;
        public static final int USER_READ = 0x0002031A;
        public static final int USER_WRITE = 0x00020044;
        public static final int USER_EXECUTE = 0x00020041;
        public static final int USER_ALL_READ_PERMISSIONS = USER_READ_GENERAL
                                                            | USER_READ_PREFERENCES
                                                            | USER_READ_LOGON
                                                            | USER_READ_ACCOUNT
                                                            | USER_LIST_GROUPS
                                                            | USER_READ_GROUP_INFORMATION
                                                            | USER_READ
                                                            | USER_ALL_ACCESS;
    }

    public static class SamrCloseHandle extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x01;
        }

        public int retval;
        public rpc.policy_handle handle;


        public SamrCloseHandle ( rpc.policy_handle handle ) {
            this.handle = handle;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            this.handle.encode(_dst);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrConnect2 extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x39;
        }

        public int retval;
        public String system_name;
        public int access_mask;
        public rpc.policy_handle handle;


        public SamrConnect2 ( String system_name, int access_mask, rpc.policy_handle handle ) {
            this.system_name = system_name;
            this.access_mask = access_mask;
            this.handle = handle;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            _dst.enc_ndr_referent(this.system_name, 1);
            if ( this.system_name != null ) {
                _dst.enc_ndr_string(this.system_name);

            }
            _dst.enc_ndr_long(this.access_mask);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrConnect4 extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x3e;
        }

        public int retval;
        public String system_name;
        public int unknown;
        public int access_mask;
        public rpc.policy_handle handle;


        public SamrConnect4 ( String system_name, int unknown, int access_mask, rpc.policy_handle handle ) {
            this.system_name = system_name;
            this.unknown = unknown;
            this.access_mask = access_mask;
            this.handle = handle;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            _dst.enc_ndr_referent(this.system_name, 1);
            if ( this.system_name != null ) {
                _dst.enc_ndr_string(this.system_name);

            }
            _dst.enc_ndr_long(this.unknown);
            _dst.enc_ndr_long(this.access_mask);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrOpenDomain extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x07;
        }

        public int retval;
        public rpc.policy_handle handle;
        public int access_mask;
        public rpc.sid_t sid;
        public rpc.policy_handle domain_handle;


        public SamrOpenDomain ( rpc.policy_handle handle, int access_mask, rpc.sid_t sid, rpc.policy_handle domain_handle ) {
            this.handle = handle;
            this.access_mask = access_mask;
            this.sid = sid;
            this.domain_handle = domain_handle;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            this.handle.encode(_dst);
            _dst.enc_ndr_long(this.access_mask);
            this.sid.encode(_dst);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.domain_handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrSamEntry extends NdrObject {

        public int idx;
        public rpc.unicode_string name;


        @Override
        public void encode ( NdrBuffer _dst ) throws NdrException {
            _dst.align(4);
            _dst.enc_ndr_long(this.idx);
            _dst.enc_ndr_short(this.name.length);
            _dst.enc_ndr_short(this.name.maximum_length);
            _dst.enc_ndr_referent(this.name.buffer, 1);

            if ( this.name.buffer != null ) {
                _dst = _dst.deferred;
                int _name_bufferl = this.name.length / 2;
                int _name_buffers = this.name.maximum_length / 2;
                _dst.enc_ndr_long(_name_buffers);
                _dst.enc_ndr_long(0);
                _dst.enc_ndr_long(_name_bufferl);
                int _name_bufferi = _dst.index;
                _dst.advance(2 * _name_bufferl);

                _dst = _dst.derive(_name_bufferi);
                for ( int _i = 0; _i < _name_bufferl; _i++ ) {
                    _dst.enc_ndr_short(this.name.buffer[ _i ]);
                }
            }
        }


        @Override
        public void decode ( NdrBuffer _src ) throws NdrException {
            _src.align(4);
            this.idx = _src.dec_ndr_long();
            _src.align(4);
            if ( this.name == null ) {
                this.name = new rpc.unicode_string();
            }
            this.name.length = (short) _src.dec_ndr_short();
            this.name.maximum_length = (short) _src.dec_ndr_short();
            int _name_bufferp = _src.dec_ndr_long();

            if ( _name_bufferp != 0 ) {
                _src = _src.deferred;
                int _name_buffers = _src.dec_ndr_long();
                _src.dec_ndr_long();
                int _name_bufferl = _src.dec_ndr_long();
                int _name_bufferi = _src.index;
                _src.advance(2 * _name_bufferl);

                if ( this.name.buffer == null ) {
                    if ( _name_buffers < 0 || _name_buffers > 0xFFFF )
                        throw new NdrException(NdrException.INVALID_CONFORMANCE);
                    this.name.buffer = new short[_name_buffers];
                }
                _src = _src.derive(_name_bufferi);
                for ( int _i = 0; _i < _name_bufferl; _i++ ) {
                    this.name.buffer[ _i ] = (short) _src.dec_ndr_short();
                }
            }
        }
    }

    public static class SamrSamArray extends NdrObject {

        public int count;
        public SamrSamEntry[] entries;


        @Override
        public void encode ( NdrBuffer _dst ) throws NdrException {
            _dst.align(4);
            _dst.enc_ndr_long(this.count);
            _dst.enc_ndr_referent(this.entries, 1);

            if ( this.entries != null ) {
                _dst = _dst.deferred;
                int _entriess = this.count;
                _dst.enc_ndr_long(_entriess);
                int _entriesi = _dst.index;
                _dst.advance(12 * _entriess);

                _dst = _dst.derive(_entriesi);
                for ( int _i = 0; _i < _entriess; _i++ ) {
                    this.entries[ _i ].encode(_dst);
                }
            }
        }


        @Override
        public void decode ( NdrBuffer _src ) throws NdrException {
            _src.align(4);
            this.count = _src.dec_ndr_long();
            int _entriesp = _src.dec_ndr_long();

            if ( _entriesp != 0 ) {
                _src = _src.deferred;
                int _entriess = _src.dec_ndr_long();
                int _entriesi = _src.index;
                _src.advance(12 * _entriess);

                if ( this.entries == null ) {
                    if ( _entriess < 0 || _entriess > 0xFFFF )
                        throw new NdrException(NdrException.INVALID_CONFORMANCE);
                    this.entries = new SamrSamEntry[_entriess];
                }
                _src = _src.derive(_entriesi);
                for ( int _i = 0; _i < _entriess; _i++ ) {
                    if ( this.entries[ _i ] == null ) {
                        this.entries[ _i ] = new SamrSamEntry();
                    }
                    this.entries[ _i ].decode(_src);
                }
            }
        }
    }

    public static class SAMPR_LOGON_HOURS extends NdrObject
    {
        public int unitsPerWeek;
        public int logonHours;


        @Override
        public void encode(NdrBuffer _dst)
        {
            _dst.align(4);
            _dst.enc_ndr_short(this.unitsPerWeek);
            _dst.enc_ndr_long(this.logonHours);
        }

        @Override
        public void decode(NdrBuffer _src)
        {
            _src.align(4);
            this.unitsPerWeek = _src.dec_ndr_short();
            this.logonHours = _src.dec_ndr_long();
        }
    }

    public static class OLD_LARGE_INTEGER extends NdrObject
    {
        public int lowPart;
        public int highPart;
        private static final long TICKS_PER_MILLISECOND = 10000;
        private static final DateTime AD_LDAP_START_DATE = new DateTime(1601, 1, 1, 0, 0, DateTimeZone.UTC);


        @Override
        public void encode(NdrBuffer _dst)
        {
            _dst.align(4);
            _dst.enc_ndr_long(this.lowPart);
            _dst.enc_ndr_long(this.highPart);
        }

        @Override
        public void decode(NdrBuffer _src)
        {
            _src.align(4);
            this.lowPart = _src.dec_ndr_long();
            this.highPart = _src.dec_ndr_long();
        }

        public long toLong()
        {
            if (lowPart < 0)
                lowPart = -lowPart;
            if (highPart < 0)
                highPart = -highPart;
            long ticksSince1601 = lowPart;
            ticksSince1601 |= ((long) highPart) << 32;

            return ticksSince1601;
        }

        public Optional<DateTime> toDate()
        {
            long MSSince1601 = this.toLong() / TICKS_PER_MILLISECOND;
            return MSSince1601 != 0 ? Optional.of(AD_LDAP_START_DATE.plus(MSSince1601)) : Optional.empty();
        }
    }

    public static class SAMPR_USER_LOGON_INFORMATION extends NdrObject
    {

        public rpc.unicode_string userName;
        public rpc.unicode_string fullName;
        public int userId;
        public int primaryGroupId;
        public rpc.unicode_string homeDirectory;
        public rpc.unicode_string homeDirectoryDrive;
        public rpc.unicode_string scriptPath;
        public rpc.unicode_string profilePath;
        public rpc.unicode_string workStations;
        public OLD_LARGE_INTEGER LastLogon;
        public OLD_LARGE_INTEGER LastLogoff;
        public OLD_LARGE_INTEGER PasswordLastSet;
        public OLD_LARGE_INTEGER PasswordCanChange;
        public OLD_LARGE_INTEGER PasswordMustChange;
        public SAMPR_LOGON_HOURS LogonHours;
        public int BadPasswordCount;
        public int LogonCount;
        public int UserAccountControl;

        public ArrayList<ArrayList<Integer>> variablesMD = new ArrayList<>();

        @Override
        public void encode(NdrBuffer _dst)
        {
            //Need to implement.
        }


        @Override
        public void decode(NdrBuffer _src) throws NdrException
        {
            decodeHeadersAndSimpleTypes(_src);
            decodeDeepTypes(_src);
        }

        private void decodeDeepTypes(NdrBuffer _src) throws NdrException
        {
            int variableIndex = 0;
            decodeUnicodeString(_src, userName, variableIndex++);
            decodeUnicodeString(_src, fullName, variableIndex++);
            decodeUnicodeString(_src, homeDirectory, variableIndex++);
            decodeUnicodeString(_src, homeDirectoryDrive, variableIndex++);
            decodeUnicodeString(_src, scriptPath, variableIndex++);
            decodeUnicodeString(_src, profilePath, variableIndex++);
            decodeUnicodeString(_src, workStations, variableIndex++);
            decodeLogonHours(_src, LogonHours, variableIndex);
        }

        public void decodeHeadersAndSimpleTypes(NdrBuffer _src)
        {
            _src.align(4);
            //decode info request type
            _src.dec_ndr_long();
            userName = decodeUnicodeStringHeader(_src, userName);
            fullName = decodeUnicodeStringHeader(_src, fullName);
            userId = _src.dec_ndr_long();
            primaryGroupId = _src.dec_ndr_long();
            homeDirectory = decodeUnicodeStringHeader(_src, homeDirectory);
            homeDirectoryDrive = decodeUnicodeStringHeader(_src, homeDirectoryDrive);
            scriptPath = decodeUnicodeStringHeader(_src, scriptPath);
            profilePath = decodeUnicodeStringHeader(_src, profilePath);
            workStations = decodeUnicodeStringHeader(_src, workStations);
            LastLogon = decodeOldLargeInteger(_src, LastLogon);
            LastLogoff = decodeOldLargeInteger(_src, LastLogoff);
            PasswordLastSet = decodeOldLargeInteger(_src, PasswordLastSet);
            PasswordCanChange = decodeOldLargeInteger(_src, PasswordCanChange);
            PasswordMustChange = decodeOldLargeInteger(_src, PasswordMustChange);
            LogonHours = decodeLogonHoursHeader(_src, LogonHours);
            BadPasswordCount = _src.dec_ndr_short();
            LogonCount = _src.dec_ndr_short();
            UserAccountControl = _src.dec_ndr_long();
        }

        private SAMPR_LOGON_HOURS decodeLogonHoursHeader(NdrBuffer _src, SAMPR_LOGON_HOURS logonHours)
        {
            _src.align(4);
            if (logonHours == null)
            {
                logonHours = new SAMPR_LOGON_HOURS();
            }
            logonHours.unitsPerWeek = _src.dec_ndr_short();
            ArrayList<Integer> currentVariableMD = new ArrayList<>();
            int variableReferentId = _src.dec_ndr_long();
            currentVariableMD.add(variableReferentId);
            variablesMD.add(currentVariableMD);

            return logonHours;
        }

        public rpc.unicode_string decodeUnicodeStringHeader(NdrBuffer _src, rpc.unicode_string stringToDecode)
        {
            _src.align(4);
            if (stringToDecode == null)
            {
                stringToDecode = new rpc.unicode_string();
            }
            stringToDecode.length = (short) _src.dec_ndr_short();
            stringToDecode.maximum_length = (short) _src.dec_ndr_short();
            ArrayList<Integer> currentVariableMD = new ArrayList<>();
            int variableReferentId = _src.dec_ndr_long();
            currentVariableMD.add(variableReferentId);
            variablesMD.add(currentVariableMD);

            return stringToDecode;
        }

        public void decodeUnicodeString(NdrBuffer _src, rpc.unicode_string stringToDecode, int variableIndex) throws NdrException
        {
            Integer referentId = variablesMD.get(variableIndex).get(0);
            if (referentId != 0)
            {
                _src = _src.deferred;
                int max_length = _src.dec_ndr_long();
                //offset
                _src.dec_ndr_long();
                int actual_length = _src.dec_ndr_long();
                int text_start_index = _src.index;
                _src.advance(2 * actual_length);

                if (stringToDecode.buffer == null)
                {
                    if (max_length < 0 || max_length > 0xFFFF)
                        throw new NdrException(NdrException.INVALID_CONFORMANCE);
                    stringToDecode.buffer = new short[max_length];
                }
                _src = _src.derive(text_start_index);
                for (int _i = 0; _i < actual_length; _i++)
                {
                    stringToDecode.buffer[_i] = (short) _src.dec_ndr_short();
                }
            }
        }

        public static OLD_LARGE_INTEGER decodeOldLargeInteger(NdrBuffer _src, OLD_LARGE_INTEGER oldLargeInteger)
        {
            if (oldLargeInteger == null)
            {
                oldLargeInteger = new OLD_LARGE_INTEGER();
            }
            oldLargeInteger.decode(_src);

            return oldLargeInteger;
        }

        public static void decodeLogonHours(NdrBuffer _src, SAMPR_LOGON_HOURS logonHours, int variableIndex)
        {
            //Need to implement
        }

        public boolean isAccountEnabled()
        {
            //If the flag is on it's disabled.
            return (UserAccountControl & 1) == 0;
        }
    }

    public static class USER_CONTROL_INFORMATION extends NdrObject
    {
        public int userAccountControl;

        @Override
        public void encode(NdrBuffer _dst)
        {
            //Need to implement
        }

        @Override
        public void decode(NdrBuffer _src)
        {
            _src.align(4);
            //Referent Id
            _src.dec_ndr_long();
            userAccountControl = _src.dec_ndr_long();
        }

        public boolean isAccountEnabled()
        {
            //If the flag is on it's disabled.
            return (userAccountControl & 1) == 0;
        }
    }

    public static class SamrQueryInformationUser2<SAMPR_INFORMATION_TYPE extends NdrObject> extends DcerpcMessage
    {
        public int retval;
        public rpc.policy_handle handle;
        public SAMPR_INFORMATION_TYPE info;
        public int queryType;
        private Supplier<SAMPR_INFORMATION_TYPE> typeSupplier;


        public int getOpnum()
        {
            return 0x2F;
        }

        public SamrQueryInformationUser2(rpc.policy_handle handle, int queryType, Supplier<SAMPR_INFORMATION_TYPE> typeSupplier)
        {
            this.handle = handle;
            this.queryType = queryType;
            this.typeSupplier = typeSupplier;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(queryType);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            int _samp = _src.dec_ndr_long();
            if (_samp != 0)
            {
                if (info == null)
                {
                    info = typeSupplier.get();
                }
                info.decode(_src);
            }
            retval = _src.dec_ndr_long();
        }
    }

    public static class SamrEnumerateAliasesInDomain extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x0f;
        }

        public int retval;
        public rpc.policy_handle domain_handle;
        public int resume_handle;
        public int acct_flags;
        public SamrSamArray sam;
        public int num_entries;


        public SamrEnumerateAliasesInDomain ( rpc.policy_handle domain_handle, int resume_handle, int acct_flags, SamrSamArray sam,
                int num_entries ) {
            this.domain_handle = domain_handle;
            this.resume_handle = resume_handle;
            this.acct_flags = acct_flags;
            this.sam = sam;
            this.num_entries = num_entries;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            this.domain_handle.encode(_dst);
            _dst.enc_ndr_long(this.resume_handle);
            _dst.enc_ndr_long(this.acct_flags);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.resume_handle = _src.dec_ndr_long();
            int _samp = _src.dec_ndr_long();
            if ( _samp != 0 ) {
                if ( this.sam == null ) { /* YOYOYO */
                    this.sam = new SamrSamArray();
                }
                this.sam.decode(_src);

            }
            this.num_entries = _src.dec_ndr_long();
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrEnumerateDomainsInSamServer extends DcerpcMessage
    {

        public int getOpnum()
        {
            return 0x06;
        }

        public int retval;
        public SamrPolicyHandle handle;
        public samr.SamrSamArray sam;
        public int resume_handle;
        public int acct_flags;
        public int num_entries;


        public SamrEnumerateDomainsInSamServer(SamrPolicyHandle handle, int resume_handle)
        {
            this.handle = handle;
            this.resume_handle = resume_handle;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(resume_handle);
            _dst.enc_ndr_long(acct_flags);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            resume_handle = _src.dec_ndr_long();
            int _samp = _src.dec_ndr_long();
            if (_samp != 0)
            {
                if (sam == null)
                {
                    sam = new samr.SamrSamArray();
                }
                sam.decode(_src);
            }
            num_entries = _src.dec_ndr_long();
            retval = _src.dec_ndr_long();
        }
    }

    public static class SamrOpenAlias extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x1b;
        }

        public int retval;
        public rpc.policy_handle domain_handle;
        public int access_mask;
        public int rid;
        public rpc.policy_handle alias_handle;


        public SamrOpenAlias ( rpc.policy_handle domain_handle, int access_mask, int rid, rpc.policy_handle alias_handle ) {
            this.domain_handle = domain_handle;
            this.access_mask = access_mask;
            this.rid = rid;
            this.alias_handle = alias_handle;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            this.domain_handle.encode(_dst);
            _dst.enc_ndr_long(this.access_mask);
            _dst.enc_ndr_long(this.rid);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.alias_handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrEnumerateGroupsInDomain extends DcerpcMessage
    {
        public int retval;
        public SamrDomainHandle handle;
        public int resume_handle;
        public samr.SamrSamArray sam;
        public int acct_flags;
        public int num_entries;


        public int getOpnum()
        {
            return 0x0b;
        }

        public SamrEnumerateGroupsInDomain(SamrDomainHandle handle, int resume_handle)
        {
            this.handle = handle;
            this.resume_handle = resume_handle;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(resume_handle);
            _dst.enc_ndr_long(acct_flags);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            resume_handle = _src.dec_ndr_long();
            int _samp = _src.dec_ndr_long();
            if (_samp != 0)
            {
                if (sam == null)
                {
                    sam = new samr.SamrSamArray();
                }
                sam.decode(_src);

            }
            num_entries = _src.dec_ndr_long();
            retval = _src.dec_ndr_long();
        }
    }

    public static class SamrOpenGroup extends DcerpcMessage
    {
        public int retval;
        public SamrDomainHandle handle;
        public int access;
        public int group_id;
        public rpc.policy_handle group_handle;

        public int getOpnum()
        {
            return 0x13;
        }

        public SamrOpenGroup(SamrDomainHandle handle, int access, int group_id)
        {
            this.handle = handle;
            this.access = access;
            this.group_id = group_id;
            this.group_handle = new rpc.policy_handle();
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(access);
            _dst.enc_ndr_long(group_id);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            this.group_handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrOpenUser extends DcerpcMessage
    {
        public int retval;
        public SamrDomainHandle handle;
        public int access;
        public int user_id;
        public rpc.policy_handle user_handle = new rpc.policy_handle();

        public int getOpnum()
        {
            return 0x22;
        }

        public SamrOpenUser(SamrDomainHandle handle, int access, int user_id)
        {
            this.handle = handle;
            this.access = access;
            this.user_id = user_id;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(access);
            _dst.enc_ndr_long(user_id);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            this.user_handle.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static class SamrEnumerateUsersInDomain extends DcerpcMessage
    {
        public int retval;
        public SamrPolicyHandle handle;
        public int resume_handle;
        public int userAccountControl = 4;
        public samr.SamrSamArray sam;
        public int acct_flags;
        public int num_entries;


        public int getOpnum()
        {
            return 0x0d;
        }

        public SamrEnumerateUsersInDomain(SamrPolicyHandle handle, int resume_handle)
        {
            this.handle = handle;
            this.resume_handle = resume_handle;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_long(resume_handle);
            _dst.enc_ndr_long(userAccountControl);
            _dst.enc_ndr_long(acct_flags);
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            resume_handle = _src.dec_ndr_long();
            int _samp = _src.dec_ndr_long();
            if (_samp != 0)
            {
                if (sam == null)
                {
                    sam = new samr.SamrSamArray();
                }
                sam.decode(_src);

            }
            num_entries = _src.dec_ndr_long();
            retval = _src.dec_ndr_long();
        }
    }


    public static class SamrLookupDomainInSamServer extends DcerpcMessage
    {

        public int retval;
        public SamrPolicyHandle handle;
        public rpc.unicode_string name;
        //out
        public rpc.sid_t domainId;

        public int getOpnum()
        {
            return 0x05;
        }

        public SamrLookupDomainInSamServer(SamrPolicyHandle handle, rpc.unicode_string name)
        {
            this.handle = handle;
            this.name = name;
            ptype = 0;
        }

        public void encode_in(NdrBuffer _dst) throws NdrException
        {
            handle.encode(_dst);
            _dst.enc_ndr_short(name.length);
            _dst.enc_ndr_short(name.maximum_length);
            _dst.enc_ndr_referent(name.buffer, 1);

            if (name.buffer != null)
            {
                _dst = _dst.deferred;
                int _dns_domain_bufferl = name.length / 2;
                int _dns_domain_buffers = name.maximum_length / 2;
                _dst.enc_ndr_long(_dns_domain_buffers);
                _dst.enc_ndr_long(0);
                _dst.enc_ndr_long(_dns_domain_bufferl);
                int _dns_domain_bufferi = _dst.index;
                _dst.advance(2 * _dns_domain_bufferl);

                _dst = _dst.derive(_dns_domain_bufferi);
                for (int _i = 0; _i < _dns_domain_bufferl; _i++)
                {
                    _dst.enc_ndr_short(name.buffer[_i]);
                }
            }
        }

        public void decode_out(NdrBuffer _src) throws NdrException
        {
            int _somainSid = _src.dec_ndr_long();
            if (_somainSid != 0)
            {
                if (domainId == null)
                {
                    domainId = new rpc.sid_t();
                }
                _src = _src.deferred;
                domainId.decode(_src);
            }
            retval = _src.dec_ndr_long();
        }
    }

    public static class SamrGetMembersInAlias extends DcerpcMessage {

        @Override
        public int getOpnum () {
            return 0x21;
        }

        public int retval;
        public rpc.policy_handle alias_handle;
        public lsarpc.LsarSidArray sids;


        public SamrGetMembersInAlias ( rpc.policy_handle alias_handle, lsarpc.LsarSidArray sids ) {
            this.alias_handle = alias_handle;
            this.sids = sids;
        }


        @Override
        public void encode_in ( NdrBuffer _dst ) throws NdrException {
            this.alias_handle.encode(_dst);
        }


        @Override
        public void decode_out ( NdrBuffer _src ) throws NdrException {
            this.sids.decode(_src);
            this.retval = _src.dec_ndr_long();
        }
    }

    public static final int SE_GROUP_MANDATORY = 1;
    public static final int SE_GROUP_ENABLED_BY_DEFAULT = 2;
    public static final int SE_GROUP_ENABLED = 4;
    public static final int SE_GROUP_OWNER = 8;
    public static final int SE_GROUP_USE_FOR_DENY_ONLY = 16;
    public static final int SE_GROUP_RESOURCE = 536870912;
    public static final int SE_GROUP_LOGON_ID = -1073741824;

    public static class SamrRidWithAttribute extends NdrObject {

        public int rid;
        public int attributes;


        @Override
        public void encode ( NdrBuffer _dst ) throws NdrException {
            _dst.align(4);
            _dst.enc_ndr_long(this.rid);
            _dst.enc_ndr_long(this.attributes);

        }


        @Override
        public void decode ( NdrBuffer _src ) throws NdrException {
            _src.align(4);
            this.rid = _src.dec_ndr_long();
            this.attributes = _src.dec_ndr_long();

        }
    }

    public static class SamrRidWithAttributeArray extends NdrObject {

        public int count;
        public SamrRidWithAttribute[] rids;


        @Override
        public void encode ( NdrBuffer _dst ) throws NdrException {
            _dst.align(4);
            _dst.enc_ndr_long(this.count);
            _dst.enc_ndr_referent(this.rids, 1);

            if ( this.rids != null ) {
                _dst = _dst.deferred;
                int _ridss = this.count;
                _dst.enc_ndr_long(_ridss);
                int _ridsi = _dst.index;
                _dst.advance(8 * _ridss);

                _dst = _dst.derive(_ridsi);
                for ( int _i = 0; _i < _ridss; _i++ ) {
                    this.rids[ _i ].encode(_dst);
                }
            }
        }


        @Override
        public void decode ( NdrBuffer _src ) throws NdrException {
            _src.align(4);
            this.count = _src.dec_ndr_long();
            int _ridsp = _src.dec_ndr_long();

            if ( _ridsp != 0 ) {
                _src = _src.deferred;
                int _ridss = _src.dec_ndr_long();
                int _ridsi = _src.index;
                _src.advance(8 * _ridss);

                if ( this.rids == null ) {
                    if ( _ridss < 0 || _ridss > 0xFFFF )
                        throw new NdrException(NdrException.INVALID_CONFORMANCE);
                    this.rids = new SamrRidWithAttribute[_ridss];
                }
                _src = _src.derive(_ridsi);
                for ( int _i = 0; _i < _ridss; _i++ ) {
                    if ( this.rids[ _i ] == null ) {
                        this.rids[ _i ] = new SamrRidWithAttribute();
                    }
                    this.rids[ _i ].decode(_src);
                }
            }
        }
    }
}
