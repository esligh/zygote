package com.yunzhu.module.common.crypto.base;

import com.yunzhu.module.common.config.Config;
import com.yunzhu.module.common.crypto.sm2.SM2EncDecUtils;

import java.io.IOException;

/**
 * @author:lisc
 * @date: 2019-05-27
 * @description: 
 */
public class SM2Strategy extends Strategy
{
    private static volatile SM2Strategy sInstance;
    private static boolean sFlag = false;
    private static final String HARD_MACHINE_KEY_HEAD = "3059301306072A8648CE3D020106082A811CCF5501822D034200";
    private SM2Strategy(){
        synchronized(SM2Strategy.class) {
            if(sFlag == false) {
                sFlag = !sFlag;
            } else {
                throw new RuntimeException("SM2Strategy only permit one instance!");
            }
        }
    }

    public static SM2Strategy getDefault()
    {
        if (sInstance == null) {
            synchronized (SM2Strategy.class) {
                if (sInstance == null) {
                    sInstance = new SM2Strategy();
                }
            }
        }
        return sInstance;
    }

    public String sm2Encrypt(String publicKey,String srcData)
    {
        try {
            String key = publicKey;
            if (key != null && key.startsWith(HARD_MACHINE_KEY_HEAD)) {
                key = key.substring(HARD_MACHINE_KEY_HEAD.length());
            }
            return SM2EncDecUtils.SM2Enc(key,srcData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sm2Decrypt(String privateKey,String encodeData)
    {
        try {
            String plainText = SM2EncDecUtils.SM2Dec(privateKey,encodeData);
            return plainText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
