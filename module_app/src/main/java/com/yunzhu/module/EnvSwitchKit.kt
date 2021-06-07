package com.yunzhu.module

import android.content.Context
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.didichuxing.doraemonkit.kit.Category
import com.yunzhu.module.bus.constant.PrefKey
import com.yunzhu.module.common.utils.PreferencesUtils
import com.yunzhu.module.common.utils.ToastUtils
import java.util.*

/**
 * 测试切换设备标识
 */
class EnvSwitchKit : AbstractKit() {
    override fun getCategory(): Int {
        return Category.BIZ
    }

    override fun getName(): Int {
        return R.string.doraemon_kit_uuid
    }

    override fun getIcon(): Int {
        return R.drawable.ic_logo
    }

    override fun onClick(context: Context) {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        PreferencesUtils.putString(context, PrefKey.DEVICE_UUID, uuid)
        ToastUtils.showShort("修改设备标识为：$uuid")
    }

    override fun onAppInit(context: Context) {}
}