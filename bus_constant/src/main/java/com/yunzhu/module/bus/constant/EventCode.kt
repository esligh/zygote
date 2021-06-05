package com.yunzhu.module.bus.constant

/**
 * @author : lisc
 * @desc: 按模块划分约定的事件code
 */
object EventCode {

    object Common {
        //业务流程结束时发送该事件通知相关页面，如充值，提现业务
        const val FLOW_FINISH = 9999
    }

    object Main {
        const val LOGIN_OVER = 5000  //登录完成
    }

    object Mine{
        const val SET_PAY_PWD_DONE = 1001 //设置/修改密码完成
        const val UPDATE_USER_AVATAR = 1002 //通知个人中心修改用户头像
        const val UNBIND_BANK_CARD = 1003 //解绑银行卡
        const val UPLOAD_ID_CARD_DONE = 1004 //上传身份证
        const val DO_REAL_NAME_SUCCESS = 1005 //实名认证
        const val BIND_CARD_DONE = 1006 //绑卡完成
        const val SET_LOGIN_PWD_DONE = 1007 //设置/修改登录密码完成
        const val RECHARGE_DONE = 1008 //充值完成
        const val WITHDRAW_CASH_DONE = 1009 //提现完成
        const val BACK_FOREGROUND = 1010
        const val BALANCE_CHANGED = 1011 //余额变动
        const val RISK_FLOW_OVER = 1012 //风控流程完成
        const val LOGIN_RISK_FLOW_OVER = 1012 //风控流程完成
        const val LOGIN_OUT = 1013 //退出登录
        const val UNION_NOTIFY_PAY_RESULT = 1014 //通知银联支付结果
    }

    object Home{
        const val PAY_DONE = 2002
        const val JS_BRIDGE_AUTH_CODE = 2003 //h5页面通知支付
        const val JS_BRIDGE_SELECT_CARD = 2004 //h5页面通知支付
        const val JS_BRIDGE_PAY = 2005 //h5页面通知支付
        const val JS_BRIDGE_CALLBACK = 2006 //回调

    }




}