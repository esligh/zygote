package com.yunzhu.module.base.tools.helper

import java.text.SimpleDateFormat
import java.util.*

/**

 * @author: lisc
 * @date: 2020-05-19 上午 10:25
 * @desc:
 */
object DateFmtHelper
{
    const val MM_DD_HH_MM_SS = "MM-dd HH:mm:ss"

    const val MM_DD_HH_MM = "MM-dd HH:mm"

    fun format(dateTime:Long,pattern:String):String
    {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateTime
        val df = SimpleDateFormat(pattern)
        return df.format(cal.time)
    }

    fun addDate(dateTime:Long,unit:Int,amount:Int):Calendar
    {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateTime
        cal.add(unit,amount)
        return cal
    }

    fun format(cal:Calendar,pattern: String):String
    {
        val df = SimpleDateFormat(pattern)
        return df.format(cal.time)
    }


}