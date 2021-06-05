package com.yunzhu.module.base.tools.helper

import java.text.DecimalFormat

object NumberFmtHelper {

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    fun number2Str(num : Float) : String{
        return DecimalFormat("0.00").format(num)
    }

}