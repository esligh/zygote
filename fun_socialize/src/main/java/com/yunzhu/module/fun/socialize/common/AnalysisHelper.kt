package com.yunzhu.module.`fun`.socialize.common

import android.content.Context
import android.text.TextUtils
import com.umeng.analytics.MobclickAgent

/**
 * @author: lisc
 * @date: 2020-09-14 下午 04:51
 * @desc: 友盟统计辅助类
 */
object AnalysisHelper
{
    /**
     * 分享
     * */
    fun appShare(context: Context)
    {
        MobclickAgent.onEvent(context, UMengEvent.ID.APP_SHARE)
    }

    /**
     * 分享类型
     * */
    fun appShareType(context: Context,type:String)
    {
        if(!TextUtils.isEmpty(type)) {
            val params: MutableMap<String, String> = HashMap()
            params["share_type"] = type
            MobclickAgent.onEvent(context, UMengEvent.ID.APP_SHARE_TYPE, params)
        }
    }

    /**
     * 分享成功
     * */
    fun appShareValid(context: Context,type:String)
    {
        val params: MutableMap<String, Any> = HashMap()
        params["share_type"] = type
        MobclickAgent.onEventObject(context, UMengEvent.ID.APP_SHARE_VALID, params)
    }

    /**
     * 打开应用
     * */
    fun openApp(context: Context)
    {
        MobclickAgent.onEvent(context, UMengEvent.ID.OPEN_APP)
    }

    /**
     * 成功打开隐藏应用
     * */
    fun openAppSuccess(context: Context)
    {
        MobclickAgent.onEvent(context, UMengEvent.ID.OPEN_APP_VALID)
    }

}