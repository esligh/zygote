package com.yunzhu.module.bus.common.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yunzhu.module.bus.common.R;
import com.yunzhu.module.common.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author:lisc
 * @date:2019-08-27
 * 安全环境监测服务：金吾卫服务
 * 1. 界面劫持监测
 * 2. HooK监测
 */
public class JinWuWeiService extends Service{
    Disposable mDisposable;

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCheckHijackTask();
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean isAppOnForeground() {
        String packageName = getPackageName();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (appProcess.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @description: 防界面劫持监测
     * @param: 
     * @return: 
     */
    private void startCheckHijackTask()
    {
        mDisposable = Observable.interval(0,1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .map(n->{
                boolean result = isAppOnForeground();
                return result;
            }).subscribe(bTop->{
                if(!bTop){
                    ToastUtils.showLong(getAppName()+getString(R.string.frame_warn_quit_foreground));
                    if(mDisposable != null) {
                        mDisposable.dispose();
                    }
                }
            });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getAppName()
    {
        String result = "";
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            result = appInfo.loadLabel(getPackageManager()) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

}
