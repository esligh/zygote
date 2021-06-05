package com.yunzhu.module.bus.common.extensions

import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter
import com.yunzhu.module.bus.constant.page.Page

/**
 * @author: lisc
 * @date: 2020-05-18 下午 02:29
 * @desc: 业务层一些公共页面的跳转帮助类
 */
object FRouter
{
    fun goHomePage()
    {
        ARouter.getInstance().build(Page.MainModule.CONTAINER)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun goLogin()
    {
        ARouter.getInstance().build(Page.MainModule.LOGIN).navigation()
    }

    fun goScanPage(activity : Activity) {
        var postcard = ARouter.getInstance().build(Page.CommonModule.SCAN_QR_CODE)
        LogisticsCenter.completion(postcard)
        var intent = Intent(activity, postcard.destination)
        intent.putExtras(postcard.extras)
        activity.startActivity(intent)
    }


}
