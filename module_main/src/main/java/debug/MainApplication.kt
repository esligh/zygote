package debug

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.rxbus.RxBus
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.yunzhu.module.bus.api.config.URL
import com.yunzhu.module.bus.common.security.KFTSafeStorage
import com.yunzhu.module.bus.constant.AppConfig
import com.yunzhu.module.bus.constant.BusTag
import com.yunzhu.module.bus.constant.EventCode
import com.yunzhu.module.common.base.BaseApplication
import com.yunzhu.module.common.common.RxEvent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.util.IInnerImageSetter
import com.yunzhu.module.base.tools.utils.L

/**
 * 组件的Application
 */
class MainApplication : BaseApplication() {

    private var activityCount = 0
    private var isRunInBackground = false

    override fun onCreate() {
        super.onCreate()
        if (com.yunzhu.module.bus.api.BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug()
            ARouter.openLog()
        }
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0) // (Optional) How many method line to show. Default 2
            .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            .tag(AppConfig.LOG_TAG) // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })
        ARouter.init(this)
//        DoraemonKit.install(this, ArrayList())
        Utils.init(this)
        initTangramEnvOnce()
        L.setLogEnable(true)
        regActivityLifecycle()
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            KFTSafeStorage.generateMaterKeyBelowM()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        URL.BASE_SERVER_URL =
            com.yunzhu.module.bus.api.BuildConfig.SERVER_BASE_URL
        URL.BASE_PREFIX =
            com.yunzhu.module.bus.api.BuildConfig.URL_PREFIX
        //dex突破65535的限制
        MultiDex.install(this)
    }

    private fun regActivityLifecycle() {
        registerActivityLifecycleCallbacks(object :
            ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {
            }

            override fun onActivityStarted(activity: Activity) {
                ++activityCount
                if (isRunInBackground) {
                    isRunInBackground = false
                    RxBus.getDefault()
                        .post(
                            RxEvent(
                                EventCode.Mine.BACK_FOREGROUND,
                                null
                            ), BusTag.Main.HOME)
                }
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {
                --activityCount
                if (activityCount == 0) {
                    isRunInBackground = true
                }
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {
            }

            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun initTangramEnvOnce() {
        TangramBuilder.init(
            this,
            object : IInnerImageSetter {
                override fun <IMAGE : ImageView> doLoadImageUrl(
                    view: IMAGE,
                    url: String?
                ) {
                    Glide.with(applicationContext)
                        .load(url).into(view)
                }
            },
            ImageView::class.java
        )
        TangramBuilder.switchLog(true)
    }

    companion object {

    }
}
