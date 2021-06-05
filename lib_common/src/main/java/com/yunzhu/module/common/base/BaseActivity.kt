package com.yunzhu.module.common.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yunzhu.module.common.R
import com.yunzhu.module.common.vm.BaseViewModel
import com.yunzhu.module.common.vm.IBaseView
import java.lang.reflect.ParameterizedType

/**
 * @author:lisc
 * @date: 2019-10-15
 * @description: MVVM Activity基类
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IBaseView {
    lateinit var mViewModel: VM
    private var toolbarLayout: ViewGroup? = null
    private var frameTitleTv: TextView? = null
    private var frameBackView: ImageView? = null
    private var frameLineView: View? = null

    //是否在前台的标记
    private val isFront = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        super.onCreate(savedInstanceState)
        inject()
        val layoutId = getLayoutId()
        if (layoutId == 0) {
            setContentView()
        } else if (!showToolbar()) {
            setContentView(layoutId)
        } else {
            setContentView(R.layout.activity_base)
            val root = findViewById<ViewGroup>(R.id.activityRootView)
            View.inflate(this, layoutId, root)
        }
        toolbarLayout = findViewById(R.id.toolbarLayout)
        frameBackView = findViewById(R.id.toolbarBack)
        frameTitleTv = findViewById(R.id.toolbarTitle)
        frameLineView = findViewById(R.id.toolbarLine)
        if (frameBackView != null) {
            frameBackView!!.setOnClickListener { v: View? -> onBackPressed() }
        }
        if (isFixOri()) {
            fixedOrientation()
        }
        if (!enableScreenShot()) {
            //禁止录屏，截屏等
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        initViewModel()
        mViewModel!!.pageIntent = intent
        lifecycle.addObserver(mViewModel!!)
        setListener()
        initView()
        registerUIChangeLiveData()
        initData()
        if (isDarkTheme()) {
            setBackIcon(R.drawable.ic_arrow_back_black)
            setTitleColorRes(R.color.black)
        } else {
            setBackIcon(R.drawable.ic_arrow_back_white)
            setTitleColorRes(R.color.white)
        }
    }

    override fun setTitle(title: CharSequence) {
        frameTitleTv?.text = title.toString()
    }

    override fun setTitle(titleId: Int) {
        frameTitleTv?.setText(titleId)
    }

    private fun fixedOrientation() {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O && Build.VERSION.SDK_INT != Build.VERSION_CODES.O_MR1) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    fun setTitleColorRes(@ColorRes colorRes: Int) {
        frameTitleTv?.setTextColor(resources.getColor(colorRes))
    }

    protected fun showDownLine(bShow: Boolean) {
        if (frameLineView != null) {
            if (bShow) {
                frameLineView!!.visibility = View.VISIBLE
            } else {
                frameLineView!!.visibility = View.GONE
            }
        }
    }

    protected fun setToolbarBackGroundColor(@ColorRes color: Int) {
        toolbarLayout?.setBackgroundColor(resources.getColor(color))
    }

    fun setBackIcon(@DrawableRes iconRes: Int) {
        frameBackView?.setImageResource(iconRes)
    }

    fun showBack(bShow: Boolean) {
        if (frameBackView != null) {
            if (bShow) {
                frameBackView!!.visibility = View.VISIBLE
            } else {
                frameBackView!!.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel!!)
    }

    private fun initViewModel() {
        val modelClass: Class<BaseViewModel>?
        val type = javaClass.genericSuperclass
        modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        mViewModel = createViewModel<BaseViewModel>(this, modelClass) as VM
    }

    @LayoutRes abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun inject()
    protected abstract fun setListener()
    protected fun setContentView() {}
    protected fun onClickMore(view: View?) {}

    open fun showToolbar(): Boolean { return true }
    open fun isFullScreen():Boolean{return false}
    open fun isFixOri():Boolean {return true}
    open fun isDarkTheme(): Boolean {return true}
    open fun enableScreenShot(): Boolean { return true}

    private fun <T : ViewModel?> createViewModel(
        activity: FragmentActivity?,
        cls: Class<T>?
    ): T {
        return ViewModelProviders.of(activity!!)[cls!!]
    }

    //注册BaseView回调
    private fun registerUIChangeLiveData() {
        mViewModel!!.baseViewLiveData.showDialogEvent!!.observe(
            this,
            Observer { titleRes -> showLoading(titleRes!!) })
        mViewModel!!.baseViewLiveData.dismissDialogEvent!!.observe(
            this,
            Observer { closeLoading() })
        mViewModel!!.baseViewLiveData.finishEvent!!.observe(
            this,
            Observer { closePage() })
        mViewModel!!.baseViewLiveData.setMessageEvent!!.observe(
            this,
            Observer { s -> setLoadingMessage(s) })
    }

    override fun closePage() {
        finish()
    }

    fun hideSystemNavigationBar() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = uiOptions
    }
}