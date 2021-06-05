package com.yunzhu.module.bus.common.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import com.blankj.rxbus.RxBus
import com.jude.swipbackhelper.SwipeBackHelper
import com.yunzhu.module.bus.common.extensions.FRouter
import com.yunzhu.module.bus.common.global.SessionMgr
import com.yunzhu.module.bus.common.service.JinWuWeiService
import com.yunzhu.module.bus.common.vm.FrameBaseViewModel
import com.yunzhu.module.bus.api.exception.ApiException
import com.yunzhu.module.bus.constant.ErrorCode
import com.yunzhu.module.common.R
import com.yunzhu.module.common.base.BaseActivity
import com.yunzhu.module.common.common.RxEvent
import com.yunzhu.module.common.utils.StatusBarUtil
import com.yunzhu.module.common.utils.ToastUtils
import com.yunzhu.module.ui.dialog.CustomInfoDialog
import com.yunzhu.module.ui.dialog.WaitingDialog

/**
 * @author:lisc
 * @date:2019-10-17
 * @desc 业务层的 Activity 基类
 */
abstract class FrameBaseActivity<VM: FrameBaseViewModel>: BaseActivity<VM>() {
    private var waitingDialog : WaitingDialog? = null
    private var isFront = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        showDownLine(showToolbarLine())
        registerRxBus()
        defaultListener()
        setUpSwipeBack()
    }

    override fun onResume() {
        super.onResume()
        isFront = true
        if (supportCheckHijack()) {
            stopService(Intent(this, JinWuWeiService::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        isFront = false
        if (supportCheckHijack()) {
            startService(Intent(this, JinWuWeiService::class.java))
        }
    }

    //是否开启界面反劫持
    open fun supportCheckHijack():Boolean { return false }

    /**
     * 判断当前页面是否正在前台显示
     * @return: true 正在前台显示   false未在前台
     * */
    fun isFront():Boolean { return isFront }

    private fun setUpSwipeBack()
    {
        //添加页面手势侧滑
        SwipeBackHelper.onCreate(this)
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
            .setSwipeBackEnable(isEnableSwipeBack())//设置是否可滑动
            .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
            .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
            .setSwipeRelateOffset(300)//activity联动时的偏移量。默认500px。
    }

    private fun defaultListener()
    {
        //未处理的异常
        mViewModel.mDefaultApiException.observe(this,androidx.lifecycle.Observer {
            if (it.errorCode == ErrorCode.NEED_LOGIN) {
                showMsgDlg(this,it.errorMessage,R.string.frame_btn_login,onDismiss = {
                    SessionMgr.clearSession()
                    FRouter.goLogin()
                })
            } else if (it.errorCode == ErrorCode.LOGIN_ON_OTHER_SIDE
                || it.errorCode == ErrorCode.TOKEN_EXPIRE
                || it.errorCode == ErrorCode.APP_ENV_CHANGED) {
                showMsgDlg(this,it.errorMessage,R.string.frame_btn_confirm,onDismiss = {
                    SessionMgr.clearSession()
                    FRouter.goLogin()
                })
            } else {
                if(!exceptionHandler(it)){
                    defaultExceptionHandler(it)
                }
            }
        })
    }

    /**
     * 设置加载框的内容
     * */
    override fun setLoadingMessage(message:String){
        if(waitingDialog != null){
            waitingDialog?.setMessage(message)
        }
    }

    /**
     * 页面需要处理的exception，如果返回false由defaultExceptionHandler进行默认处理
     *
     * */
    open fun exceptionHandler(apiException: ApiException):Boolean{ return false}

    /**
     * @param: apiException
     * @desc: 异常处理的默认handler弹出Toast显示
     * */
    open fun defaultExceptionHandler(apiException: ApiException) {
        if (!TextUtils.isEmpty(apiException.errorMessage)) {
            ToastUtils.showShort(apiException.errorMessage)
        }
    }

    /**
     * @desc: 是否允许侧滑，当为false时需要同时指定Activity theme中android:windowIsTranslucent为false
     * */
    open fun isEnableSwipeBack():Boolean = false

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    open fun setToolbarColor(@ColorRes colorRes:Int)
    {
        setToolbarBackGroundColor(colorRes)
    }

    /**
     * @param：
     * @desc：设置沉浸式状态栏
     * */
    open fun setStatusBar()
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        StatusBarUtil.setTranslucentStatus(this)
        StatusBarUtil.setStatusBarDarkTheme(this, isDarkTheme())
    }

    /**
     * @param：
     * @desc：设置沉浸式状态的颜色
     * */
    fun setStatusBarColor(@ColorRes colorRes:Int)
    {
        var color = ActivityCompat.getColor(this, colorRes)
        StatusBarUtil.setStatusBarColor(this, color)
    }

    /**
     * @param：
     * @desc：设置状态栏主题
     * */
    fun setStatusBarTheme(bDarkTheme:Boolean = true)
    {
        StatusBarUtil.setStatusBarDarkTheme(this, bDarkTheme)
    }

    /**
     * @param messageRes 加载框的提示内容
     * @desc 页面默认的加载框
     * */
    override fun showLoading(@StringRes messageRes: Int) {
        val isShowing = waitingDialog?.isShowing ?: false
        if (waitingDialog == null) {
            waitingDialog = WaitingDialog(this, getString(messageRes))
        }

        if(!isShowing){
            waitingDialog?.setCancelable(false)
            waitingDialog?.show()
        }
    }

    override fun inject() {}
    
    override fun closeLoading() {
        if (waitingDialog != null) {
            waitingDialog?.dismiss()
        }
    }

    /**
     * @desc 注册页面自有的Bus Tag，注册后，发送该tag的事件时该页面可以接受
     * */
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
    fun postEvent(eventCode: Int,any:Any? = null) {
        postEvent(RxEvent(eventCode, any))
    }

    /**
     * @param tag 指向特定页面的tag,如果此页面需要接受带此tag的事件，需要重写regRxBusTag()
     * @param event 事件对象
     * @return:
     * */
    fun postEventWithTag(tag:String,event: RxEvent) {
        RxBus.getDefault().post(event,tag)
    }

    /**
     * @param tag 指向特定页面的tag,如果此页面需要接受带此tag的事件，需要重写regRxBusTag()
     * @param eventCode 事件对象code
     * @param any 事件的负载数据
     * @return:
     * */
    fun postEventWithTag(tag:String,eventCode:Int,any:Any? = null) {
        postEventWithTag(tag,
            RxEvent(eventCode, any)
        )
    }


    override fun initData() {}

    open fun onBusEvent(what: Int, event: Any?) {

    }

    open fun showToolbarLine():Boolean = true

    /**
     * @param context
     * @param msgRes 错误提示内容
     * @param btnListener
     * @param onDismiss
     * @desc 内容提示框
     * */
    fun showMsgDlg(context:Context,@StringRes msgRes:Int,btnListener:(Dialog)->Unit={},onDismiss:()->Unit={}) {
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
    fun showMsgDlg(context: Context, msg:String?, @StringRes btnTextRes:Int=R.string.frame_btn_confirm,btnListener:(Dialog)->Unit={}, onDismiss:()->Unit={}) {
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

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
        SwipeBackHelper.onDestroy(this)
    }
}

