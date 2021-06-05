package com.yunzhu.module.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.yunzhu.module.ui.R
import kotlinx.android.synthetic.main.frame_dialog_waiting.*

class WaitingDialog(context: Context?, private val mMsg: String) :
    Dialog(context!!, R.style.waitingDialogStyle) {

    private var mMsgTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_dialog_waiting)
        msgTv.text = mMsg
        mainLayout.background.alpha = 210
        setCanceledOnTouchOutside(false)
    }

    fun setMessage(@StringRes messageRes: Int) {
        if (mMsgTv != null) {
            mMsgTv!!.setText(messageRes)
        }
    }

    fun setMessage(message: String?) {
        if (mMsgTv != null) {
            mMsgTv!!.text = message
        }
    }

}