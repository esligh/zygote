package com.yunzhu.module.bus.common.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import androidx.annotation.StringRes
import com.blankj.rxbus.RxBus
import com.yunzhu.module.bus.common.extensions.FRouter
import com.yunzhu.module.bus.common.global.SessionMgr
import com.yunzhu.module.bus.common.vm.FrameBaseViewModel
import com.yunzhu.module.bus.api.exception.ApiException
import com.yunzhu.module.bus.constant.ErrorCode
import com.yunzhu.module.common.R
import com.yunzhu.module.common.base.BaseFragment
import com.yunzhu.module.common.common.RxEvent
import com.yunzhu.module.common.utils.ToastUtils
import com.yunzhu.module.ui.dialog.CustomInfoDialog
import com.yunzhu.module.ui.dialog.WaitingDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author:lisc
 * @date:2019-10-17
 * @desc：支持懒加载的KFT Fragment基类
 */
abstract class FrameBaseFragment<VM : FrameBaseViewModel>: BaseFragment<VM>()
{
    private var waitingDialog : WaitingDialog? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    private var isFront = false

    override fun showLoading(@StringRes messageRes: Int) {
        if (waitingDialog == null) {
            waitingDialog = WaitingDialog(activity, getString(messageRes))
        }
        waitingDialog?.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerRxBus()
        defaultListener()
    }

    override fun closeLoading() {
        waitingDialog?.dismiss()
    }

    override fun setLoadingMessage(message: String?) {
        waitingDialog?.setMessage(message)
    }

    override fun onResume() {
        super.onResume()
        isFront = true
    }

    override fun onPause() {
        super.onPause()
        isFront = false
    }
    fun isFront():Boolean{ return isFront }

    override fun closePage() {
        activity?.finish()
    }

    override fun inject() {

    }

    //注册页面自有的Bus Tag，注册后，发送该tag的事件自有该页面可以接受
    open fun regRxBusTag():String {
        return ""
    }

    private fun registerRxBus() {
        RxBus.getDefault().subscribe(this, object : RxBus.Callback<RxEvent>() {
            override fun onEvent(rxEvent: RxEvent) {
                onBusEvent(rxEvent.what,rxEvent.event)
            }
        })
        if(!TextUtils.isEmpty(regRxBusTag())){
            RxBus.getDefault().subscribe(this,regRxBusTag(),object :RxBus.Callback<RxEvent>(){
                override fun onEvent(rxEvent: RxEvent) {
                    onBusEvent(rxEvent.what,rxEvent.event)
                }
            })
        }
    }

    private fun defaultListener()
    {
        //未处理的异常
        mViewModel.mDefaultApiException.observe(this,androidx.lifecycle.Observer {
            if(!exceptionHandler(it)){
                defaultExceptionHandler(it)
            }
        })
    }

    /**
     * 页面需要处理的exception，如果返回false由defaultExceptionHandler进行默认处理
     * */
    open fun exceptionHandler(apiException: ApiException):Boolean{ return false}

    /**
     * @param: apiException
     * @desc: 异常处理的默认handler弹出Toast显示
     * */
    open fun defaultExceptionHandler(apiException: ApiException){
        if(!TextUtils.isEmpty(apiException.errorMessage)){
            ToastUtils.showShort(apiException.errorMessage)
        }
    }

    /**
     * @param context
     * @param msg 错误提示内容
     * @param btnListener
     * @param onDismiss
     * @desc 内容提示框
     * */
    fun showMsgDlg(context: Context, @StringRes msgRes:Int, btnListener:(Dialog)->Unit={}, onDismiss:()->Unit={})
    {
        showMsgDlg(context,context.getString(msgRes),
            R.string.frame_btn_confirm,btnListener, onDismiss)
    }

    /**
     * @param context
     * @param msg 错误提示内容
     * @param btnTextRes 按钮文字
     * @param btnListener
     * @param onDismiss
     * @desc 内容提示框
     * */
    fun showMsgDlg(context: Context, msg:String?, @StringRes btnTextRes:Int=R.string.frame_btn_confirm, btnListener:(Dialog)->Unit={}, onDismiss:()->Unit={})
    {
        val builder = CustomInfoDialog.Builder(context)
        builder.setBtn(context.getString(btnTextRes)) {
            it.dismiss()
            btnListener(it)
        }
        builder.setContentGravity(Gravity.CENTER)
        builder.setBtnColorRes(com.yunzhu.module.ui.R.color.colorPrimary)
        builder.setMessage(msg ?: "")
        val dialog = builder.create()
        dialog.setOnDismissListener {
            onDismiss()
        }
        dialog.show()
    }

    /**
     * @param event 事件对象
     * @desc 发送全局事件对象，所有的页面重写onBusEvent方法后都可以接受到该方法发送的事件
     * @see RxEvent
     * */
    fun postEvent(event: RxEvent) {
        RxBus.getDefault().post(event)
    }

    /**
     * @param eventCode 事件码
     * @param any 事件承载的数据
     * @desc 发送全局事件对象，所有的页面重写onBusEvent方法后都可以接受到该方法发送的事件
     * @see RxEvent
     * */
    fun postEvent(eventCode: Int,any:Any? = null)
    {
        postEvent(RxEvent(eventCode, any))
    }

    /**
     * @param tag 指向特定页面的tag,如果此页面需要接受带此tag的事件，需要重写regRxBusTag()
     * @param event 事件对象
     * @return:
     * */
    fun postEventWithTag(tag:String,event: RxEvent)
    {
        RxBus.getDefault().post(event,tag)
    }

    /**
     * @param tag 指向特定页面的tag,如果此页面需要接受带此tag的事件，需要重写regRxBusTag()
     * @param eventCode 事件对象code
     * @param any 事件的负载数据
     * @return:
     * */
    fun postEventWithTag(tag:String,eventCode: Int,any:Any? = null)
    {
        postEventWithTag(tag,
            RxEvent(eventCode, any)
        )
    }

    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }


    open fun onBusEvent(what: Int, event: Any?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        RxBus.getDefault().unregister(this)
    }

    /**该方法支持数据的懒加载*/
    override fun initData() {}

    override fun isLazy(): Boolean  = true
}