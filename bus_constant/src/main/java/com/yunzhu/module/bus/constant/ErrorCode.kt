package com.yunzhu.module.bus.constant

/**
 * @author: lisc
 * @desc: 服务端接口的错误码
 */
object ErrorCode {

    const val ERROR_CODE_PREFIX = "PASP_BIZ_"

    const val TOKEN_EXPIRE = "EC_KFTPPG_00000003" //token失效 检查登录
    const val NEED_LOGIN = "EC_UMS_BIZ_20031" //重新登录
    const val APP_ENV_CHANGED = "EC_UMS_BIZ_20032" //用户操作环境已变化，请重新登陆",
    const val NOT_REGISTER = "EC_UMS_BIZ_20037" //账号尚未注册
    const val LOGIN_ON_OTHER_SIDE = "EC_KFTPPG_00000005" // 其他设备抢登录

    const val BALANCE_NOT_ENOUGH = "EC_PPC_30000007" //余额不足

    const val PASP_BIZ_500013 = "PASP_BIZ_500013"//未设置密码
    const val PASP_BIZ_300006 = "PASP_BIZ_300006"//密码验证失败
    const val PASP_BIZ_300007 = "PASP_BIZ_300007"//超过当日密码验证最大次数
    const val PASP_BIZ_500012 = "PASP_BIZ_500012"//未上传证
    const val PASP_BIZ_500016 = "PASP_BIZ_500016"//身份证过期
    const val PASP_BIZ_500022 = "PASP_BIZ_500022"//提示添加银行卡

    const val ERROR_SMS_CODE = "CPF_CHANNEL_008"//错误的验证码
    const val ERROR_PAY_PWD = "EC_KFTCM_30000005" //支付密码错误
    const val NO_BIND_CARD = "EC_PPC_30000032" //未绑卡


}