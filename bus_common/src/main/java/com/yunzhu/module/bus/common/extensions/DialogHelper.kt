package com.yunzhu.module.bus.common.extensions

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import androidx.annotation.StringRes
import com.yunzhu.module.ui.R
import com.yunzhu.module.ui.dialog.CustomAlertDialog
import com.yunzhu.module.ui.dialog.CustomInfoDialog

/**

 * @author: lisc
 * @date: 2020-04-01 下午 02:29
 * @desc: 接管App的所有公共弹框
 */
object DialogHelper {

    /**
     * @description: 用户风控措施确认
     * @param:
     * @return:
     */
    fun userRiskConfirm(context: Context, msg: String?,
                         onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={})
    {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_confirm)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    fun showNoticeDlg(context: Context,  @StringRes titleRes:Int,msg:String?,
                      @StringRes btnTextRes:Int= R.string.frame_btn_confirm,
                      btnListener:(Dialog)->Unit={}, onDismiss:()->Unit={})
    {
        val builder = CustomInfoDialog.Builder(context)
        builder.setBtn(context.getString(btnTextRes)) {
            it.dismiss()
            btnListener(it)
        }
        builder.setTitle(context.getString(titleRes))
        builder.setContentGravity(Gravity.CENTER)
        builder.setBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg ?: "")
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.setOnDismissListener {
            onDismiss()
        }
        dialog.show()
    }


    /**
     * @description: 注册
     * @param:
     * @return:
     */
    fun questionRegister(context: Context, msg: String?,
                         onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={})
    {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_go_reg)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 余额不足
     * @param:
     * @return:
     */
    fun questionBalanceNotEnough(context: Context, msg: String?,
                                 onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={})
    {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_change_pay_card)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 密码验证超过最大次数
     * @param:
     * @return:
     */
    fun questionInputPwdExceed(context: Context, msg: String?,
         onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_forget_pwd)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 询问是否忘记密码
     * @param:
     * @return:
     */
    fun questionForgetPwd(context: Context, msg: String?,
         onConfirm:(dlg: Dialog)->Unit={},onRetry:(dlg:Dialog)->Unit={},onCancel:()->Unit={}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_forget_pwd)) {
            it.dismiss()
            onConfirm(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_retry)) {
            it.dismiss()
            onRetry(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(msg)
        val dialog = builder.create()
        dialog.setOnCancelListener {
            onCancel()
        }
        dialog.show()
    }

    /**
     * @description: 询问是否上传个人身份照
     * @param: 未上传/已过期
     * @return:
     */
    fun questionUploadIdCard(context: Context,@StringRes messageRes: Int,
                             onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={}) {
        questionUploadIdCard(context,context.getString(messageRes),onConfirm,onCancel)
    }

    /**
     * @description: 询问是否上传个人身份照
     * @param: 未上传/已过期
     * @return:
     */
    fun questionUploadIdCard(context: Context,message: String?,
        onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }
        builder.setPosBtn(context.getString(R.string.frame_btn_upload)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 询问是否添加银行卡
     * @param:
     * @return:
     */
    fun questionAddCard(context: Context,message: String?,
        onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={},onDismiss:()->Unit = {}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }
        builder.setPosBtn(context.getString(R.string.frame_btn_add_card)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.setOnDismissListener {
            onDismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 询问是否设置密码
     * @param:
     * @return:
     */
    fun questionSetPwd(context: Context,message: String?,
        onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_setting)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    /**
     * @description: 询问是否实名认证
     * @param:
     * @return:
     */
    fun questionRealName(context: Context,msg: Int,
                       onConfirm:(dlg: Dialog)->Unit={},onCancel:(dlg:Dialog)->Unit={}) {
        val builder = CustomAlertDialog.Builder(context)
        builder.setNegBtn(context.getString(R.string.frame_btn_cancel)) {
            it.dismiss()
            onCancel(it)
        }

        builder.setPosBtn(context.getString(R.string.frame_btn_go_real_name)) {
            it.dismiss()
            onConfirm(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setPosBtnColorRes(R.color.colorPrimary)
        builder.setMessage(context.getString(msg))
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

}