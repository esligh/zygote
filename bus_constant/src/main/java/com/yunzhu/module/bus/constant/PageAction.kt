package com.yunzhu.module.bus.constant

/**

 * @author: lisc
 * @date: 2019-12-24 下午 05:30
 * @desc: 定义同一页面的不同角色，按组件划分
 */
object PageAction
{
    object VerifyIdPage {
        const val SET_PAY_PWD = 1001
        const val FORGET_PAY_PWD = 1002
    }

    object VerifyPwdPage{
        const val MODIFY_PAY_PWD = 2001
        const val VERIFY_BEFORE_ADD_NEW_CARD = 2002
    }

    object InputPwdPage
    {
        const val SET_PAY_PWD = 3000
        const val FORGET_PAY_PWD = 3001
        const val MOD_PAY_PWD = 3002
    }

    object ModPayPwdPage
    {
        const val SET_PWD = 5001 //设置支付密码
        const val MOD_PWD = 5002 //修改支付密码
        const val FORGET_PWD_BIND_NEW_CARD = 5003 //忘记密码-绑定新卡
        const val FORGET_PWD_BIND_OLD_CARD = 5004 //忘记密码-绑定旧卡
        const val FORGET_PWD = 5005
    }

    object OperStatusPage
    {
        const val REG = 4000
        const val MOD_LOGIN_PWD = 4001
        const val UNBIND_CARD = 4002
        const val ADD_BANK_CARD = 4003
    }

    object PwdInputPage
    {
        const val UNBIND_CARD = 5000 //解绑卡验证密码
    }

    object AddBankCardPage
    {
        const val FORGET_PAY_PWD = 6000
        const val ADD_CARD = 6001
    }

    object BankCardCollectInfoPage
    {
        const val FORGET_PAY_PWD_BIND_NEW_CARD = 7000 //忘记密码-绑定新卡
        const val BIND_NEW_CARD = 7001 //绑定新卡
    }

    object SetLoginPwdPage
    {
        const val REGISTER = 8000
        const val FORGET_PWD = 8001
        const val SET_PWD = 8002
    }

    object VerifySmsCodePage
    {
        const val FORGET_LOGIN_PWD = 9000
        const val FORGET_PAY_PWD = 9002
        const val SET_PAY_PWD = 9003
    }

    object RiskCtrlSmsVerify
    {
        const val LOGIN_RISK_CTRL = 9004
        const val PAY_RISK_CTRL = 9005
        const val PASSIVE_PAY_RISK_CTRL = 9006
    }

    object PayPage
    {
        const val RECHARGE = 10000             //充值
        const val WITHDRAW = 10001             //提现
        const val INITIATIVE_SCAN_PAY = 10002 //主扫支付
        const val PASSIVE_SCAN_PAY = 10003    //被扫支付
        const val PENDING_ORDER_PAY = 1004  //待支付订单
        const val UNION_INITIATIVE_SCAN_PAY = 10005 //银联主扫支付
    }

    object TradeStatusPage
    {
        const val RECHARGE = 11000
        const val WITHDRAW = 11001
    }

    object ProtocolPage
    {
        const val USER_PROTOCOL = 12000
        const val PRIVACY_POLICY = 12001
        const val USER_REG_PROTOCOL = 12002
    }
}