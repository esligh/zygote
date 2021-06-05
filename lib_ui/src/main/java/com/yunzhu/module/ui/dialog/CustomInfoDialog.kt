package com.yunzhu.module.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.yunzhu.module.ui.R
import com.yunzhu.module.ui.dialog.CustomInfoDialog
import kotlinx.android.synthetic.main.frame_dialog_custom_info_dialog.*

/**
 * @author:lisc
 * @date:2019-06-14
 */
class CustomInfoDialog(context: Context) : Dialog(context) {
    private var btnText: String? = null
    private var message: CharSequence? = null
    private var title: String? = null
    private var btnListener: ((CustomInfoDialog) -> Unit)? = null
    private var bntColorRes = 0
    private var contentGravity = -1
    private var bShowTitle = true

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_dialog_custom_info_dialog)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        titleTv.text = title
        contentTv.text = message
        btn.text = btnText
        if (bShowTitle) {
            titleTv.visibility = View.VISIBLE
        } else {
            titleTv.visibility = View.GONE
        }

        if (bntColorRes > 0) {
            btn.setTextColor(context.resources.getColor(bntColorRes))
        }

        btn.setOnClickListener(View.OnClickListener {
            btnListener?.invoke(this@CustomInfoDialog)
        })

        if (contentGravity > 0) {
            contentTv.gravity = contentGravity
        }
    }

    fun setBtnColorRes(@ColorRes resId: Int) {
        bntColorRes = resId
    }

    fun setBtn(name: String?, l: ((CustomInfoDialog) -> Unit)?) {
        btnText = name
        btnListener = l
    }

    fun setMessage(msg: CharSequence?) {
        message = msg
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setContentGravity(gravity: Int) {
        contentGravity = gravity
    }

    fun showTitle(bShow: Boolean) {
        bShowTitle = bShow
    }

    class Builder(var mContext: Context) {
        var btnText: String? = null
        var title: String? = null
        var message: CharSequence? = null
        var btnListener: ((CustomInfoDialog) -> Unit)? = null
        var btnColorRes = 0
        var contentGravity = 0
        var bShowTitle = false
        fun setBtn(name: String?, l: ((CustomInfoDialog) -> Unit)?): Builder {
            btnText = name
            btnListener = l
            return this
        }

        fun setBtnColorRes(@ColorRes resId: Int): Builder {
            btnColorRes = resId
            return this
        }

        fun setTitle(title: String?): Builder {
            this.title = title
            bShowTitle = true
            return this
        }

        fun setMessage(msg: CharSequence?): Builder {
            message = msg
            return this
        }

        fun setContentGravity(gravity: Int): Builder {
            contentGravity = gravity
            return this
        }

        fun showTitle(bShow: Boolean): Builder {
            bShowTitle = bShow
            return this
        }

        fun create(): CustomInfoDialog {
            val dialog = CustomInfoDialog(mContext)
            dialog.setTitle(title)
            dialog.setMessage(message)
            dialog.setBtn(btnText, btnListener)
            dialog.setBtnColorRes(btnColorRes)
            dialog.setContentGravity(contentGravity)
            dialog.showTitle(bShowTitle)
            return dialog
        }

        fun show() {
            val dialog = create()
            dialog.show()
        }

    }
}