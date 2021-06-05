package com.yunzhu.module.common.base;

import androidx.annotation.Keep;

@Keep
public interface IApplicationDelegate {

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
