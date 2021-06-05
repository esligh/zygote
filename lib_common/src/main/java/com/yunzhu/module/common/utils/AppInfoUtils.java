package com.yunzhu.module.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2019-05-14.
 */

public class AppInfoUtils {

    public final static String SHA1 = "SHA1";
    public final static String MD5 = "MD5";
    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param type
     *
     * @return
     */
    public static String getSignInfo(Context context, String type) {
        String tmp = null;
        Signature[] signs = getSignatures(context, context.getPackageName());
        for (Signature sig : signs) {
            if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
                break;
            }else if(MD5.equals(type)){
                tmp = getSignatureString(sig, MD5);
                break;
            }
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     *
     * @param context
     * @param packageName
     *
     * @return
     */
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     * @param sig
     * @param type
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes) {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fingerprint;
    }
}
