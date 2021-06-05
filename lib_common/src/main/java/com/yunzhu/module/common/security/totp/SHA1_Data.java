package com.yunzhu.module.common.security.totp;

public interface SHA1_Data {
    /**
     *  constant defined in "SECURE HASH STANDARD"
     */
    static final int H0 = 0x67452301;
    /**
     *  constant defined in "SECURE HASH STANDARD"
     */
    static final int H1 = 0xEFCDAB89;
    /**
     *  constant defined in "SECURE HASH STANDARD"
     */
    static final int H2 = 0x98BADCFE;
    /**
     *  constant defined in "SECURE HASH STANDARD"
     */
    static final int H3 = 0x10325476;
    /**
     *  constant defined in "SECURE HASH STANDARD"
     */
    static final int H4 = 0xC3D2E1F0;
    /**
     * offset in buffer to store number of bytes in 0-15 word frame
     */
    static final int BYTES_OFFSET = 81;
    /**
     * offset in buffer to store current hash value
     */
    static final int HASH_OFFSET = 82;
    /**
     * # of bytes in H0-H4 words; <BR>
     * in this implementation # is set to 20 (in general # varies from 1 to 20)
     */
    static final int DIGEST_LENGTH = 20;
}