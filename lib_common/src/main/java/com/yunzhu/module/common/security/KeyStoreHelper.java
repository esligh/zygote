package com.yunzhu.module.common.security;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

/**
 * @author:lisc
 * @date:2020-03-17
 * @desc: KeyStore工具类，目前支持RSA加密
 *  使用步骤：
 *  1.生成密钥
 *  KeyStoreHelper.genKeyStoreKey(BaseApplication.Companion.getInstance(),keyAlias);
 *  2.加密
 *  String enSrc = KeyStoreHelper.encryptRSA(keyAlias, src);
 *  3.解密
 *  String result = KeyStoreHelper.decryptRSA(keyAlias,enSrc);
 */
public class KeyStoreHelper {
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";

    /**
     * @param: context
     * @param: keyAlias 的别名
     * @desc: 在KeyStore中生成Key值
     * */
    public static void genKeyStoreKey(Context context,String keyAlias) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateRSAKey_AboveApi23(keyAlias);
        } else {
            generateRSAKey_BelowApi23(context,keyAlias);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void generateRSAKey_AboveApi23(String keyAlias) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                .Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build();

        keyPairGenerator.initialize(keyGenParameterSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        keyPair.getPrivate().getEncoded();
    }

    private static void generateRSAKey_BelowApi23(Context context,String keyAlias) throws Exception{
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 30);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias(keyAlias)
                .setSubject(new X500Principal("CN=" + keyAlias))
                .setSerialNumber(BigInteger.TEN) // 签名在有效日期范围内
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        keyPairGenerator.initialize(spec);
        keyPairGenerator.generateKeyPair();
    }

    /**
     * @param: keyAlias 的别名
     * @param: plainText 待加密的字符串
     * @result: 返回通过KeyStore加密后的密文
     * */
    public static String encryptRSA(String keyAlias,String src) throws Exception {
        return encryptRSA(keyAlias,src.getBytes());
    }

    /**
     * @param: keyAlias 的别名
     * @param: plainText 待加密的字符串字节数组
     * @result: 返回通过KeyStore加密后的密文
     * */
    public static String encryptRSA(String keyAlias,byte[] plainText) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
        keyStore.load(null);
        PublicKey publicKey = keyStore.getCertificate(keyAlias).getPublicKey();

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedByte = cipher.doFinal(plainText);
        return Base64.encodeToString(encryptedByte, Base64.DEFAULT);
    }

    /**
     * @param: keyAlias 的别名，该别名和加密时的别名需要一致
     * @param: encryptedText 密文
     * @result: 返回通过KeyStore加密后的密文
     * */
    public static String decryptRSA(String keyAlias,String encryptedText) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);
        keyStore.load(null);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, null);

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

    /**
     * 生成随机的AES主密钥
     * */
    public static String generatorMasterKey(int keySize,long seed)
    {
        String key = "";
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom random=new SecureRandom();
            random.setSeed(seed);
            generator.init(keySize, random);
            SecretKey secretKey=generator.generateKey();
            key = Base64.encodeToString(secretKey.getEncoded(),Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

}
