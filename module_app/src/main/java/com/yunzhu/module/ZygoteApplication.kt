package com.yunzhu.module

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.widget.ImageView
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.rxbus.RxBus
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.mob.MobSDK
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.util.IInnerImageSetter
import com.yunzhu.module.base.tools.utils.L
import com.yunzhu.module.bus.api.config.URL.BASE_PREFIX
import com.yunzhu.module.bus.api.config.URL.BASE_SERVER_URL
import com.yunzhu.module.bus.common.security.KFTSafeStorage.generateMaterKeyBelowM
import com.yunzhu.module.bus.constant.AppConfig
import com.yunzhu.module.bus.constant.BusTag
import com.yunzhu.module.bus.constant.EventCode
import com.yunzhu.module.common.base.BaseApplication
import com.yunzhu.module.common.common.RxEvent
import dagger.hilt.android.HiltAndroidApp
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom
import java.security.Security
import java.util.*
import javax.crypto.KeyGenerator

/**
 * @author:lisc
 * @version:1.0.0
 * @desc: App实例对象
 * */
@HiltAndroidApp
class ZygoteApplication : BaseApplication() {

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
                return BuildConfig.LOG_DEBUG
            }
        })
        ARouter.init(this)
        val kits: MutableList<AbstractKit> = ArrayList()
        kits.add(EnvSwitchKit())
        DoraemonKit.install(this, kits)
        Utils.init(this)
        MobSDK.init(this)
        initTangramEnvOnce()
        L.setLogEnable(BuildConfig.LOG_DEBUG)
        installSecurityProvider()
        regActivityLifecycle()
        init()
        val myPackageName = getMyProcessName()
        if(myPackageName == getProcessName(this)){ }
    }

    private fun init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            generateMaterKeyBelowM()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        BASE_SERVER_URL = com.yunzhu.module.bus.api.BuildConfig.SERVER_BASE_URL
        BASE_PREFIX = com.yunzhu.module.bus.api.BuildConfig.URL_PREFIX
        //dex突破65535的限制
        MultiDex.install(this)
    }

    private fun regActivityLifecycle() {
        registerActivityLifecycleCallbacks(object :
            ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {}

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
        //初始化tangram环境 只需要初始化一次
        TangramBuilder.init(this, object : IInnerImageSetter {
                override fun <IMAGE : ImageView> doLoadImageUrl(
                    view: IMAGE,
                    url: String?
                ) {
                    Glide.with(this@ZygoteApplication)
                        .load(url).into(view)
                }
            }, ImageView::class.java)
        TangramBuilder.switchLog(true)
    }

    private fun getMyProcessName():String
    {
        return ""
    }

    private fun getProcessName(context: Context): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return ""
        for (proInfo in runningApps) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName
                }
            }
        }
        return ""
    }

    companion object {
        private fun installSecurityProvider() {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                L.d("current environment has not BouncyCastleProvider")
                Security.addProvider(BouncyCastleProvider())
            }
            val version1 = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).version
            L.d("version=$version1")
            if (Security.getProvider("BC") == null) {
                Security.addProvider(BouncyCastleProvider())
            }
            if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) != null) {
                val version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).version
                L.d("current version=$version")
            }
            try {
                val kg = KeyGenerator.getInstance("SM4", BouncyCastleProvider.PROVIDER_NAME)
                kg.init(64, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchProviderException) {
                e.printStackTrace()
            }
        }
    }
}