package com.yunzhu.module.common.security.totp;

import com.yunzhu.module.common.crypto.sm2.Util;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static kotlin.text.Typography.tm;
import static org.junit.Assert.*;

/**
 * @author: lisc
 * @date: 2020-07-02 下午 02:34
 * @desc:
 */
public class GoogleAuthenticatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void calculateCode() {
        byte[] key = { 11,91,-107,90,2,36,-45,3,-80,-57 };
//        byte[] data = { 0,0,1,115,14,48,-58,-73 };
        byte[] data = new byte[8];
        long value = 1593670944439L;

        for(int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte)((int)value);
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA512");
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(signKey);
            byte[] hash = mac.doFinal(data);
            int offset = hash[hash.length - 1] & 15;
            long truncatedHash = 0L;

            for(int i = 0; i < 4; ++i) {
                truncatedHash <<= 8;
                truncatedHash |= (long)(hash[offset + i] & 255);
            }

            truncatedHash &= 2147483647L;
            truncatedHash %= 1000000L;
            int result = (int)truncatedHash;
            System.out.println(result);
//            System.out.println(Util.byteToHex(hash));

            //3F603C4128F96917F1824267B58D19816087929C1FCCDE665AF7C56F40B6F3BD9C07B1E9C34AB50D01A23A9C2328D6E04FBAE8B90EFF54FD20FD202F03D767B6
            //3F603C4128F96917F1824267B58D19816087929C1FCCDE665AF7C56F40B6F3BD9C07B1E9C34AB50D01A23A9C2328D6E04FBAE8B90EFF54FD20FD202F03D767B6

            //3f603c4128f96917f1824267b58d19816087929c1fccde665af7c56f40b6f3bd9c07b1e9c34ab50d01a23a9c2328d6e04fbae8b90eff54fd20fd202f03d767b6
        } catch (InvalidKeyException | NoSuchAlgorithmException var14) {
            throw new GoogleAuthenticatorException("The operation cannot be performed now.");
        }
    }
}