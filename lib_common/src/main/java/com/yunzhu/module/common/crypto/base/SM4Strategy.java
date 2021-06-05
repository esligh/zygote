package com.yunzhu.module.common.crypto.base;


import com.yunzhu.module.common.crypto.sm2.Util;
import com.yunzhu.module.common.crypto.sm4.SM4Util;

/**
 * @author:lisc
 * @date: 2019-05-27
 * @description: 
 */
public class SM4Strategy extends Strategy
{
    private static volatile SM4Strategy sInstance;
    private static SM4Util sm4;

    private static boolean sFlag = false;

    private SM4Strategy(){
        synchronized(SM4Strategy.class) {
            if(sFlag == false) {
                sFlag = !sFlag;
            } else {
                throw new RuntimeException("SM4Strategy only permit one instance!");
            }
        }
    }

    public static SM4Strategy getDefault()
    {
        if(sInstance == null){
            synchronized (SM4Strategy.class){
                if(sInstance == null){
                    sInstance = new SM4Strategy();
                    sm4 = SM4Util.newInstance();
                }
            }
        }
        return sInstance;
    }

    public String sm4EncryptCBC(String srcData,String key,String iv)
    {
        try {
            byte[] encryptData = sm4.encrypt_Cbc_Padding(Util.hexStringToBytes(key),
                    Util.hexStringToBytes(iv),
                    srcData.getBytes());
            String result = Util.getHexString(encryptData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sm4DecryptCBC(String encodeData,String key,String iv)
    {
        try {
            byte[] decodeData = sm4.decrypt_Cbc_Padding(Util.hexStringToBytes(key),
                    Util.hexStringToBytes(iv),
                    Util.hexStringToBytes(encodeData));
            return new String(decodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String sm4EncryptECB(String srcData,String key)
    {
        try {
            byte[] da = srcData.getBytes();
            byte[] encryptData = sm4.encrypt_Ecb_NoPadding(Util.hexStringToBytes(key), da);
            String result = Util.getHexString(encryptData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sm4DecryptECB(String encodeData,String key)
    {
        try {
            byte[] decodeData = sm4.decrypt_Ecb_NoPadding(Util.hexStringToBytes(key),
                    Util.hexStringToBytes(encodeData));
            return new String(decodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String sm4EncryptECBPadding(String srcData,String key)
    {
        try {
            byte[] da = srcData.getBytes();
            byte[] encryptData = SM4Util.encrypt_Ecb_Padding(Util.hexStringToBytes(key), da);
            String result = Util.getHexString(encryptData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sm4DecryptECBPadding(String encodeData,String key)
    {
        try {
            byte[] decodeData = SM4Util.decrypt_Ecb_Padding(
                Util.hexStringToBytes(key),
                Util.hexStringToBytes(encodeData)
            );
            return new String(decodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
