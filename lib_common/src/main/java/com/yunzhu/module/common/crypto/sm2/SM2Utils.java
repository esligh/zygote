package com.yunzhu.module.common.crypto.sm2;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SM2Utils {
    //生成随机秘钥对
    public static Map<String,String> generateKeyPair(){
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD(); //解密密钥
        ECPoint publicKey = ecpub.getQ();  //加密密钥
        Map<String,String> resutlMap = new HashMap<String,String>();
//
//        System.out.println("privateKey :" + SecurityUtils.byteToHex(privateKey.toByteArray()));
//        System.out.println("publicKey:" + SecurityUtils.byteToHex(publicKey.getEncoded()));
        resutlMap.put("privateKey", SecurityUtils.byteToHex(privateKey.toByteArray()));
        resutlMap.put("publicKey",SecurityUtils.byteToHex(publicKey.getEncoded()));
        return resutlMap;
    }

    //数据加密
    public static String encrypt(byte[] publicKey, byte[] data) throws IOException
    {
        if (publicKey == null || publicKey.length == 0)
        {
            return null;
        }

        if (data == null || data.length == 0)
        {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);

        //C1 C2 C3拼装成加密字串
        System.out.println("c1:"+SecurityUtils.byteToHex(c1.getEncoded()));
        System.out.println("c2:"+SecurityUtils.byteToHex(source));
        System.out.println("c3:"+SecurityUtils.byteToHex(c3));
//        return SecurityUtils.byteToHex(c1.getEncoded()) + SecurityUtils.byteToHex(source) + SecurityUtils.byteToHex(c3);
        return SecurityUtils.byteToHex(c1.getEncoded()) + SecurityUtils.byteToHex(c3) + SecurityUtils.byteToHex(source);

    }

    //数据解密
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException
    {
        if (privateKey == null || privateKey.length == 0)
        {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0)
        {
            return null;
        }
        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = SecurityUtils.byteToHex(encryptedData);
        /***分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
        int c2Len = encryptedData.length - 97;



        byte[] c1Bytes = SecurityUtils.hexToByte(data.substring(0,130));
        byte[] c2 = SecurityUtils.hexToByte(data.substring(130,130 + 2 * c2Len));
        byte[] c3 = SecurityUtils.hexToByte(data.substring(130 + 2 * c2Len,194 + 2 * c2Len) );


        System.out.println("c1:"+SecurityUtils.byteToHex(c1Bytes));
        System.out.println("c2:"+SecurityUtils.byteToHex(c2));
        System.out.println("c3:"+SecurityUtils.byteToHex(c3));


        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);

        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        //返回解密结果
        return c2;
    }


}
