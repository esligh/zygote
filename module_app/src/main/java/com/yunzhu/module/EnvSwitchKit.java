package com.yunzhu.module;

import android.content.Context;

import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.Category;
import com.yunzhu.module.bus.constant.PrefKey;
import com.yunzhu.module.common.utils.PreferencesUtils;
import com.yunzhu.module.common.utils.ToastUtils;
import com.yunzhu.module.config.AppConfig;

import java.util.UUID;

/**
 * 测试切换设备标识
 * */
public class EnvSwitchKit extends AbstractKit {

    @Override
    public int getCategory() {
        return Category.BIZ;
    }
 
    @Override
    public int getName() {
        return R.string.doraemon_kit_uuid;
    }
 
    @Override
    public int getIcon() {
        return R.drawable.ic_logo;
    }
 
    @Override
    public void onClick(Context context) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        PreferencesUtils.putString(context, PrefKey.DEVICE_UUID,uuid);
        ToastUtils.showShort("修改设备标识为："+uuid);
    }
 
    @Override
    public void onAppInit(Context context) {
    
    }
}