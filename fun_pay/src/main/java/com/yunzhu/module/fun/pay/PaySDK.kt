package com.yunzhu.module.`fun`.pay

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Message
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.yunzhu.module.`fun`.pay.bean.PayInfo
import com.yunzhu.module.`fun`.pay.bean.WechatPayParams
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * @author: lisc
 * @date: 2020-10-21 下午 07:54
 * @desc: 支付SDK
 */
object PaySDK
{
    const val ALI_SDK_PAY_FLAG = 100

    /**
     * 微信支付
     * @param context
     * @param payInfo
     * */
    fun wechatPay(context: Context, payInfo: PayInfo)
    {
        var payParams = Gson().fromJson<WechatPayParams>(payInfo.apiStr,WechatPayParams::class.java)
        var mWXApi = WXAPIFactory.createWXAPI(context, payParams.appId)
        mWXApi.registerApp(payParams.appId)
        val request = PayReq()
        request.appId = payParams.appId
        request.partnerId = payParams.partnerId
        request.prepayId = payParams.prepayId
        request.packageValue = "Sign=WXPay"
        request.nonceStr = payParams.nonceStr
        request.timeStamp = payParams.timeStamp
        request.sign = payParams.paySign
        mWXApi.sendReq(request)
    }

    /**
     * 支付宝支付
     * @param activity
     * @param payInfo
     * @param handler
     * */
    fun aliPay(activity: Activity, payInfo:PayInfo,handler: Handler)
    {
        val payRunnable = Runnable {
            val aliPay = PayTask(activity)
            val result: Map<String, String> = aliPay.payV2(payInfo.apiStr, true)
            val msg = Message()
            msg.what = ALI_SDK_PAY_FLAG
            msg.obj = result
            handler.sendMessage(msg)
        }
        val payThread = Thread(payRunnable)
        payThread.start()
    }
}