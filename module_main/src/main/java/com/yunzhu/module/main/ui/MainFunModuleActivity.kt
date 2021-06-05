package com.yunzhu.module.main.ui

import android.Manifest
import com.alibaba.android.arouter.facade.annotation.Route
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.extensions.Router
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_fun_module.*
/**
 * 功能组件列表首页
 * */
@Route(path = Page.MainModule.FUN_MODULE_LIST)
class MainFunModuleActivity : FrameBaseActivity<EmptyViewModel>() {

    override fun getLayoutId(): Int  = R.layout.main_activity_fun_module

    override fun initView() {
        title = getString(R.string.main_fun_module_list_title)
    }

    override fun setListener() {
        mapModuleTv.setOnClickListener {
            val rxPermission = RxPermissions(this)
            rxPermission.request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                .subscribe {
                    Router.goPage(Page.MainModule.FUN_MAP)
                }
        }
    }
}