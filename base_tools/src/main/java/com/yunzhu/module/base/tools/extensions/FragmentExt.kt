package com.yunzhu.module.base.tools.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.showSoftInput(view: View) {
    activity?.showSoftInput(view)
}

fun Fragment.hideSoftInput() {
    activity?.hideSoftInput()
}

fun Fragment.getString(@StringRes id:Int):String {
    return resources.getString(id)
}

fun Fragment.getColor(@ColorRes resId:Int):Int {
    return ContextCompat.getColor(activity!!,resId)
}

fun Fragment.getDimen(@DimenRes resId:Int):Float
{
    return resources.getDimension(resId)
}
