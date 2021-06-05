package com.yunzhu.module.bus.api.config

import com.yunzhu.module.bus.api.BuildConfig


/**
 * @author:lisc
 * @date: 2019-06-10
 * @description: 定义各组件的服务接口URL
 */
object URL {
    var BASE_SERVER_URL = BuildConfig.SERVER_BASE_URL
    var BASE_PREFIX = BuildConfig.URL_PREFIX//默认的基础前缀，如果组件有自己的公共前缀请自己定义

    //主模块
    object Main {
        const val CHECK_LOGIN = "/user/login/check"
        const val LOGIN = "/user/login/password"
        const val SMS_LOGIN = "/user/login/phone"
        const val REGISTER = "/user/register"
        const val REQ_SMS_CODE = "/getSmsCode"
        const val SMS_CODE_VERIFY = "/authSmsCode"
        const val LOGOUT = "/user/logout"
        const val HEARTBEAT = "/heartbeat"
        const val GET_KEY = "/user/paypwd/sm2"
        const val VERIFY_PAY_PWD = "/user/paypwd/verify"
        const val CHECK_VERSION = "/source/app/wallet/android/version.json"
        const val REQ_RISK_LOGIN_SMS = "/user/login/riskcontrol/smsapply"
        const val RISK_LOGIN_SMS_VERIFY = "/user/login/riskcontrol/smsconfirm"
    }

    //我的模块
    object Mine{
        const val SET_PAY_PWD = "/user/paypwd/firstset"
        const val UPDATE_PAY_PWD = "/user/paypwd/set"
        const val UPDATE_LOGIN_PWD = "/user/loginpwd/modify"
        const val SET_LOGIN_PWD = "/user/loginpwd/set"
        const val RESET_LOGIN_PWD = "/user/loginpwd/reset"
        const val REQ_BIND_CARD = "/bankcard/bind/apply"
        const val BIND_CARD_CONFIRM = "/bankcard/bind/confirm"
        const val UNBIND_CARD = "/bankcard/unbind"
        const val QUERY_CARD_BIN = "/bankcard/queryCardBin"
        const val QUERY_CARD_TOKEN = "/bankcard/relatecardtoken"
        const val QUERY_BANK_CARD_LIST = "/bankcard/list"
        const val REQ_BIND_CARD_VERIFY_IDENTITY = "/user/paypwd/bindcard/apply"
        const val BIND_CARD_VERIFY_IDENTITY = "/user/paypwd/bindcard/confirm"
        const val REQ_SMS_VERIFY_IDENTITY = "/user/paypwd/sms/apply"
        const val SMS_VERIFY_IDENTITY = "/user/paypwd/sms/confirm"
        const val PWD_VERIFY_IDENTITY = "/user/paypwd/verify"
        const val REQ_CHECK_BANK_CARD = "/user/paypwd/originalcard/apply"
        const val CHECK_BANK_CARD_CONFIRM = "/user/paypwd/originalcard/confirm"
        const val QUERY_CUSTOMER_INFO = "/user/personinfo/query"
        const val SET_PERSONAL_INFO = "/user/personinfo/set"
        const val DO_CERTIFICATION = "/user/idcard/auth"
        const val UPLOAD_ID_CARD = "/user/idcard/ocrvalidate"
        const val CHECK_IDENTITY = "/user/paypwd/custInfo/verify"
        const val QUERY_BILL_LIST = "/account/bill/list"
        const val QUERY_BILL_DETAIL = "/account/bill/detail"
        const val QUERY_BALANCE_LIST = "/account/detail"
        const val QUERY_BALANCE = "/account/balance"
        const val RECHARGE = "/account/recharge"
        const val WITHDRAW_CASH = "/account/withdraw"
        const val PAY_LIST = "/user/payway"
        const val MAKE_TRADE_ORDER = "/order/createinner"
        const val PAY = "/order/pay"
        const val CREATE_OCR_ORDER = "/user/ocr/thirdsigndata"
        const val NOTIFY_OCR_RESULT = "/user/ocr/push"
        const val THIRD_PART_APP = "/api/tac/execute/thirdservice"

        const val CHECK_RISK_POLICY = "/order/riskcontrol/check"
        const val REQ_ORDER_RISK_SMS = "/order/riskcontrol/smsapply"
        const val ORDER_RISK_SMS_VERIFY = "/order/riskcontrol/smsconfirm"
        const val USER_RISK_CONFIRM = "/order/riskcontrol/doubleconfirm"
        const val VERIFY_PAY_PWD = "/order/verifypaypwd"
    }

    //主页模块
    object Home{
        const val CREATE_CODE_FACTOR = "/qrcode/factor/create"
        const val CREATE_SCAN_CODE = "/qrcode/create"
        const val QUERY_MERCHANGE_ORDER = "/qrcode/order/collectCode/query"
        const val SCAN_CODE_MAKE_ORDER = "/qrcode/order/activeScan/create"
        const val GET_CODE_PAY_PROGRESS = "/qrcode/order/progress"
        const val APPLY_AUTH_CODE = "/user/webpageauth/apply"
        const val APPLY_USER_INFO_AUTH_CODE = "/user/webpageauth/apply/userinfo"
        const val CONFIRM_AUTH_CODE = "/user/webpageauth/confirm"
        const val QUERY_PENDING_ORDER = "/signed/api/order/pendingpayment"
        const val CHECK_WHITE_LIST = "/domain/checkwhitelist"
        const val QUERY_HOME_TAB = "/api/tac/execute/appTab"
        const val QUERY_HOME_DATA = "/api/tac/execute/shop"
        const val UNION_AUTH_SIGNATURE = "/order/unionpay/authsignature"
        const val UNION_PAY_WAY = "/order/unionpay/payway"
        const val UNION_ACTIVE_JS_PAY = "/order/unionpay/activejspay"
    }

    object Protocol
    {
        const val USER_REG_PROTOCOL = "/source/html/agreement/registrationserviceagreement.html"
        const val PRIVACY_POLICY = "/source/html/agreement/privacypolicy.html"
        const val WARM_TIPS = "/source/html/agreement/friendlyreminders.html"
        const val USER_PROTOCOL = "/source/html/agreement/paymentaccountserviceagreement.html"
        const val WARM_TIPS_REG = "/source/html/agreement/friendlyreminders(zc-mm).html"
        const val WARM_TIPS_NAME_AUTH = "/source/html/agreement/friendlyreminders(smrz).html"
    }

}