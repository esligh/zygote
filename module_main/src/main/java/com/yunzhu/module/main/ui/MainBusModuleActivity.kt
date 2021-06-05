package com.yunzhu.module.main.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.main.R

@Route(path = Page.MainModule.BUS_MODULE_LIST)
class MainBusModuleActivity : FrameBaseActivity<EmptyViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
    }

    override fun getLayoutId(): Int  = R.layout.main_activity_bus_module

    override fun initView() {
        title = getString(R.string.main_bus_module_list_title)
    }

    override fun setListener() {

    }
}