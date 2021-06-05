package com.yunzhu.module.`fun`.socialize.common

import android.content.Context
import com.mob.MobSDK

/**

 * @author: lisc
 * @date: 2021-03-25 下午 03:14
 * @desc:
 */
object ShareSDK {

    fun initShareSdk(context: Context)
    {
        MobSDK.init(context)
    }
}