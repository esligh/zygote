package com.yunzhu.module.common.utils;

/**
 * 处理手机号码
 */
public class PhoneNoUtils {

    /**
     * 15111111111 -> 151****1111
     * @param phoneNumber
     * @return
     */
    public static String maskPhoneNo(String phoneNumber) {
        if (!StringUtils.isEmpty(phoneNumber) && phoneNumber.length()==11) {

            return phoneNumber.substring(0, 3) +
                    "****" +
                    phoneNumber.substring(7);
        }
        return "";
    }
}
