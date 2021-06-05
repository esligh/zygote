//package com.yunzhu.module.common.utils;
//
//
//import com.yunzhu.module.common.security.totp.GoogleAuthenticator;
//import com.yunzhu.module.common.security.totp.GoogleAuthenticatorConfig;
//import com.yunzhu.module.common.security.totp.HmacHashFunction;
//import com.orhanobut.logger.Logger;
//
///**
// * @author: lisc
// * @date: 2020-03-23 上午 11:49
// * @desc: TOTP (Time One-Time Password)基于时间同步的一次性口令，动态口令
// */
//public class OTPUtils {
//
//    private OTPUtils(){}
//
//    /**
//     * @param : id 条码ID (a)
//     * @param : type 付款方式(b)
//     * @param : token 条码token，用于生成动态口令(p)
//     * @param:  组织机构编码
//     * @desc : 生成条码 = （机构代码+支付标记） 支付标记原文 = (a×99+b)×99999+p
//     * 该接口主要为app在线生成条码接口，当APP无法或者不能使用离线二维码时，可以通过该接口获得用户条码，系统处理步骤如下：
//     * 步骤 1.检查支付方式是否有效，无效则返回用户失败，提示“付款方式不支持，请重新选择”。
//     * 步骤 2.查询支付账户最近生效的条码因子，当没有生效的，则生成新的条码因子，过程如下：
//     *      a)生成条码ID，条码ID通过8位循环序列自增生成。
//     *      b)生成条码token，token由数字、大写、小写字母随机产生，长度24位。
//     *      c)调用加密机，对条码token进行SM4加密，实现加密存储。
//     *      a)设定条码有效期为当前时间+24小时。
//     *      b)保存用户条码因子到数据库。
//     * 步骤 3.生成动态口令，过程如下：
//     *      a)获取动态口令刷新周期，默认为30S。
//     *      b)设置动态口令长度为6位。
//     *      c)以条码token为秘钥调用“Google Authenticator OTP算法”生成动态口令。
//     * 步骤 4.生成支付标记，算法如下：
//     *      a)设条码ID为a，付款方式为b，动态口令为p，则支付标记原文c=(a×99+b)×99999+p。
//     *      b)将c对半拆分获得数字d（1-9位）以及e（9-18位），
//     *      c)异或运算获得f=d^e;
//     *      d)最终支付标记g=”f”+”e”，当g不足18位时前面补“0”;
//     * 步骤 5.获取机构代码，生成条码（机构代码+支付标记）。
//     * 步骤 6.返回条码。
//     *
//     * @param id            条码ID
//     * @param type          支付方式
//     * @param token         条码token
//     * @param kftAgencyCode 商户码
//     * @return
//     */
//    public static String createQrCodePayBarcode(long id,int type,String token, String kftAgencyCode){
//        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder builder = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
//        builder.setHmacHashFunction(HmacHashFunction.HmacSHA512);
//        builder.setWindowSize(10);
////        builder.setCodeDigits(codeDigits);
//        GoogleAuthenticator auth = new GoogleAuthenticator(builder.build());
//        int dynamic = auth.getTotpPassword(token);
//
//        //Logger.i(">>> 条码ID：[{"+id+"}]，秘钥token:[{"+token+"}]，生成动态口令为：[{"+dynamic+"}]");
//        long originalPassword = (id*99+type)*999999+dynamic;
//        String strOriginalPassword = Long.toString(originalPassword);
//        String afterSection = strOriginalPassword.substring(strOriginalPassword.length()- 8);
//        String beforeSection = strOriginalPassword.substring(0, strOriginalPassword.length()- 8);
//
//        //前八补0
//        StringBuffer beforeSectionStr = new StringBuffer();
//        beforeSectionStr.append(beforeSection);
//        int beforLength = 8 - beforeSectionStr.length();
//        for(int i=0;i< beforLength;i++){
//            //填充0
//            beforeSectionStr.insert(0,"0");
//        }
//        //后八补0
//        StringBuffer afterSectionStr = new StringBuffer();
//        afterSectionStr.append(afterSection);
//        int afterLength = 8 - afterSectionStr.length();
//        for(int i=0;i< afterLength;i++){
//            //填充0
//            afterSectionStr.insert(0,"0");
//        }
//        String xorSection = xor(beforeSectionStr.toString(), afterSectionStr.toString());
//        StringBuffer code = new StringBuffer();
//        code.append(xorSection);
//        code.append(afterSectionStr);
//        int len = code.length();
//        for(int i=0;i< 16-len;i++){
//            //填充0
//            code.insert(0,"0");
//        }
//        code.insert(0,kftAgencyCode);
//        return code.toString();
//    }
//
//
//    /**
//     * 分解支付条码获取条码信息
//     * @param payMark
//     * @return
//     */
//    public static void getQrCodePayMarkInfo(String payMark, String kftAgencyCode){
//        //测试代码 生成需要注释掉
////        logger.info("校验支付原文：[{}]", payMark);
//        //QrCodePayMarkInfoDTO qrCodePayMarkInfoDTO = new QrCodePayMarkInfoDTO();
//        // 获取支付标记
//        payMark = payMark.substring(kftAgencyCode.length());
//
//        long beforSection = Long.parseLong(payMark.substring(0, payMark.length()- 8));
//
//        long afterSection = Long.parseLong(payMark.substring(payMark.length()- 8));
//
//        //前八补0
//        StringBuffer beforSectionStr = new StringBuffer();
//        beforSectionStr.append(beforSection);
//        int beforLength = 8 - beforSectionStr.length();
//        for(int i=0;i< beforLength;i++){
//            //填充0
//            beforSectionStr.insert(0,"0");
//        }
//        //后八补0
//        StringBuffer afterSectionStr = new StringBuffer();
//        afterSectionStr.append(afterSection);
//        int length = 8 - afterSectionStr.length();
//        for(int i=0;i< length;i++){
//            //填充0
//            afterSectionStr.insert(0,"0");
//        }
//
//        //异或运算
//        String xorSection = xor(beforSectionStr.toString(), afterSectionStr.toString());
//        long originalPassword = Long.parseLong(xorSection+""+afterSectionStr);
//        int dynamic = (int) (originalPassword % 999999);
//        int type = (int) (((originalPassword-dynamic)/999999)%99);
//        int id = (int) (((originalPassword-dynamic)/999999 -type)/99);
//
///*      qrCodePayMarkInfoDTO.setDynamicPwd(String.valueOf(dynamic));
//        qrCodePayMarkInfoDTO.setPayMark(payMark);
//        qrCodePayMarkInfoDTO.setPayType(type);
//        qrCodePayMarkInfoDTO.setQrCodeId((long)id);*/
//        //测试代码 生成需要注释掉
//        //if(logger.isDebugEnabled()){
//            Logger.i(">>> 校验动态口令：{"+dynamic+"},拆分前8位：{"+beforSection+"},拆分后8位：{"+afterSection+"}，异域运算结果：{"+xorSection+"}，付款方式：{"+type+"}");
//        //}
//        //return qrCodePayMarkInfoDTO;
//    }
//
//
//    /**
//     * 位数抑或运算，遇到8和9就取前八位位数值
//     * @param beforeStr
//     * @param afterStr
//     * @return
//     */
//    private static String xor(String beforeStr, String afterStr){
//        char[] beforeChars = beforeStr.toCharArray();
//        char[] afterChars = afterStr.toCharArray();
////      logger.info("前八数组：[{}]，后八数组：[{}]", beforChars, afterChars);
//        StringBuffer xorStr = new StringBuffer();
//        for(int i = 0;i < 8;i ++){
//            int beforeNum = Integer.valueOf(beforeChars[i] + "");
//            int afterNum = Integer.valueOf(afterChars[i] + "");
//            int xorNum = 0;
//            if(beforeNum == 8 || beforeNum == 9 || afterNum == 8 || afterNum == 9){
//                xorNum = beforeNum;
//            }else{
//                xorNum = beforeNum ^ afterNum;
//            }
//            xorStr.append(xorNum);
//        }
//        return xorStr.toString();
//    }
//}
