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
import com.yunzhu.module.ui.dialog.CustomAlertDialog
import kotlinx.android.synthetic.main.frame_dialog_custom_alert_dialog.*

/**
 * @author:lisc
 * @date:2019-06-14
 */
class CustomAlertDialog(context: Context) :
    Dialog(context) {

    private var message: CharSequence? = null
    private var title: String? = null
    private var posBntListener: ((CustomAlertDialog) -> Unit)? = null
    private var negBntListener: ((CustomAlertDialog) -> Unit)? = null
    private var negBntColorRes = 0
    private var posBtnColorRes = 0
    private var contentGravity = -1
    private var bShowTitle = true
    private var negBtnText:String? = null
    private var posBtnText:String? = null

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_dialog_custom_alert_dialog)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        titleTv.text = title
        contentTv.text = message
        negativeBtn.text = negBtnText
        if (bShowTitle) {
            titleTv.visibility = View.VISIBLE
        } else {
            titleTv.visibility = View.GONE
        }
        if (negBntColorRes > 0) {
            negativeBtn.setTextColor(context.resources.getColor(negBntColorRes))
        }
        negativeBtn.setOnClickListener(View.OnClickListener {
            negBntListener?.invoke(this@CustomAlertDialog)
        })
        if (contentGravity > 0) {
            contentTv.gravity = contentGravity
        }
        positiveBtn.text = posBtnText
        if (posBtnColorRes > 0) {
            positiveBtn.setTextColor(context.resources.getColor(posBtnColorRes))
        }
        positiveBtn.setOnClickListener(View.OnClickListener {
            posBntListener?.invoke(this@CustomAlertDialog)
        })
    }

    fun setPosBtn(name: String?, onClick:((CustomAlertDialog) -> Unit)?) {
        posBtnText = name
        posBntListener = onClick
    }

    fun setPosBtnColorRes(@ColorRes resId: Int) {
        posBtnColorRes = resId
    }

    fun setNegBtnColorRes(@ColorRes resId: Int) {
        negBntColorRes = resId
    }

    fun setNegBtn(
        name: String?,
        onClick:((CustomAlertDialog) -> Unit)?
    ) {
        negBtnText = name
        negBntListener = onClick
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
        var posBtnText: String? = null
        var negBtnText: String? = null
        var title: String? = null
        var message: CharSequence? = null
        var posBntListener: ((CustomAlertDialog) -> Unit)? = null
        var negBntListener: ((CustomAlertDialog) -> Unit)? = null
        var negBntColorRes = 0
        var posBtnColorRes = 0
        var contentGravity = 0
        var bShowTitle = false

        fun setPosBtn(name: String?, onClick:((CustomAlertDialog) -> Unit)?): Builder {
            posBtnText = name
            posBntListener = onClick
            return this
        }

        fun setNegBtn(name: String?, onClick:((CustomAlertDialog) -> Unit)?): Builder {
            negBtnText = name
            negBntListener = onClick
            return this
        }

        fun setPosBtnColorRes(@ColorRes resId: Int): Builder {
            posBtnColorRes = resId
            return this
        }

        fun setNegBtnColorRes(@ColorRes resId: Int): Builder {
            negBntColorRes = resId
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

        fun create(): CustomAlertDialog {
            val dialog = CustomAlertDialog(mContext)
            dialog.setTitle(title)
            dialog.setMessage(message)
            dialog.setNegBtn(negBtnText, negBntListener)
            dialog.setPosBtn(posBtnText, posBntListener)
            dialog.setNegBtnColorRes(negBntColorRes)
            dialog.setContentGravity(contentGravity)
            dialog.setPosBtnColorRes(posBtnColorRes)
            dialog.showTitle(bShowTitle)
            return dialog
        }

        fun show() {
            val dialog = create()
            dialog.show()
        }
    }
}