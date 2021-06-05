package com.yunzhu.module.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author:lisc
 * @date:2019-06-18
 */

public class CheckUtils {
    public static final int IDENTITYCODE_OLD = 15; // 老身份证15位
    public static final int IDENTITYCODE_NEW = 18; // 新身份证18位

    private CheckUtils(){}

    /**
     * @description: 验证输入的身份证号是否合法
     * @param:
     * @return:
     */
    public static boolean isValidId(String id){
        if(TextUtils.isEmpty(id) || id.length() != IDENTITYCODE_NEW){
            return false;
        }

        try {
            return IdcardUtils.validateIdCard(id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isLetterDigit(String str) {
        if(str == null)
            return false;
        String regex1=".*[a-zA-Z]+.*";
        String regex2=".*[0-9]+.*";
        return str.matches(regex1) && str.matches(regex2);
    }

    public static boolean isValidEmail(String str)
    {
        if(str == null)
            return false;
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return str.matches(regex);
    }

    /**
     * 验证手机号码是否合法
     * 176, 177, 178;
     * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189;
     * 145, 147;
     * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139;
     * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     *
     * "13"代表前两位为数字13,
     * "[0-9]"代表第二位可以为0-9中的一个,
     * "[^4]" 代表除了4
     * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    public static boolean isValidePhoneNum(String mobiles) {
        String telRegex = "^((1[3-9][0-9]))\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
//        return !TextUtils.isEmpty(mobiles) && mobiles.length() == 11 && mobiles.startsWith("1");
    }

    public static boolean isValidBankCard(String cardNo)
    {
        if(TextUtils.isEmpty(cardNo) || cardNo.length() < 12){
            return false;
        }
        return true;
    }

    private static boolean isIDCardNo(String id) {
        if (id == null)
            return false;
        id = id.toUpperCase();
        if (id.length() != 15 && id.length() != 18) {
            return false;
        }
        int y = 0, m = 0, d = 0;
        if (id.length() == 15) {
            y = Integer.parseInt("19" + id.substring(6, 8), 10);
            m = Integer.parseInt(id.substring(8, 10), 10);
            d = Integer.parseInt(id.substring(10, 12), 10);
        } else if (id.length() == 18) {
            if (id.indexOf("X") >= 0 && id.indexOf("X") != 17) {
                return false;
            }
            char verifyBit = 0;
            int sum = (id.charAt(0) - '0') * 7 + (id.charAt(1) - '0') * 9 + (id.charAt(2) - '0') * 10
                    + (id.charAt(3) - '0') * 5 + (id.charAt(4) - '0') * 8 + (id.charAt(5) - '0') * 4
                    + (id.charAt(6) - '0') * 2 + (id.charAt(7) - '0') * 1 + (id.charAt(8) - '0') * 6
                    + (id.charAt(9) - '0') * 3 + (id.charAt(10) - '0') * 7 + (id.charAt(11) - '0') * 9
                    + (id.charAt(12) - '0') * 10 + (id.charAt(13) - '0') * 5 + (id.charAt(14) - '0') * 8
                    + (id.charAt(15) - '0') * 4 + (id.charAt(16) - '0') * 2;
            sum = sum % 11;
            switch (sum) {
                case 0:
                    verifyBit = '1';
                    break;
                case 1:
                    verifyBit = '0';
                    break;
                case 2:
                    verifyBit = 'X';
                    break;
                case 3:
                    verifyBit = '9';
                    break;
                case 4:
                    verifyBit = '8';
                    break;
                case 5:
                    verifyBit = '7';
                    break;
                case 6:
                    verifyBit = '6';
                    break;
                case 7:
                    verifyBit = '5';
                    break;
                case 8:
                    verifyBit = '4';
                    break;
                case 9:
                    verifyBit = '3';
                    break;
                case 10:
                    verifyBit = '2';
                    break;

            }

            if (id.charAt(17) != verifyBit) {
                return false;
            }
            y = Integer.parseInt(id.substring(6, 10), 10);
            m = Integer.parseInt(id.substring(10, 12), 10);
            d = Integer.parseInt(id.substring(12, 14), 10);
        }

        int currentY = Calendar.getInstance().get(Calendar.YEAR);

        /*
         * if(isGecko){ currentY += 1900; }
         */
        if (y > currentY || y < 1870) {
            return false;
        }
        if (m < 1 || m > 12) {
            return false;
        }
        if (d < 1 || d > 31) {
            return false;
        }
        return true;
    }
}
