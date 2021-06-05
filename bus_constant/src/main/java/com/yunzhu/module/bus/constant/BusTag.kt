package com.yunzhu.module.bus.constant

/**
 * @author: lisc
 * @date: 2020-04-28 下午 02:49
 * @desc: 事件TAG，用于标记处理事件的对象，业务组件依赖的事件TAG在此定义
 */
object BusTag {

    //业务Main组件
    object Main
    {
        const val HOME = "home_page"
        const val MERCHANT_WEB_PAGE = "merchant_web_page"
        const val UNION_MERCHANT_WEB_PAGE = "union_merchant_web_page"
    }


}