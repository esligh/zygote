package com.yunzhu.module.base.tools.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Activity.hideSoftInput() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Activity.hideSoftInput(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftInput(view:View)
{
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if(imm != null) {
        view.requestFocus()
        imm.showSoftInput(view,0)
    }
}

fun Activity.showSoftInput()
{
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(0,0)
}

fun Activity.getString(@StringRes id:Int):String
{
    return resources.getString(id)
}

fun Activity.getColor(@ColorRes resId:Int):Int
{
    return ContextCompat.getColor(this,resId)
}

fun Activity.getDimen(@DimenRes resId:Int):Float
{
    return resources.getDimension(resId)
}

var Activity.windowBrightness
    get() = window.attributes.screenBrightness
    set(brightness) {
        window.attributes = window.attributes.apply {
            screenBrightness = if (brightness > 1.0 || brightness < 0) -1.0F else brightness
        }
    }

