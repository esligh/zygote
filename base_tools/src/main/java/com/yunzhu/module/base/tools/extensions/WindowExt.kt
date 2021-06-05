package com.yunzhu.module.base.tools.extensions

import android.os.Build
import android.view.View
import android.view.Window

private fun Window.hideSystemNavigationBar() {
    if (Build.VERSION.SDK_INT in 12..18) {
        decorView.gone()
    } else if (Build.VERSION.SDK_INT >= 19) {
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }
}