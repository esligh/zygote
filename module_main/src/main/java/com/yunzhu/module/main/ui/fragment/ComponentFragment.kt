package com.yunzhu.module.main.ui.fragment

import com.yunzhu.module.bus.common.base.FrameBaseFragment
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.extensions.Router
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_fragment_component.*

class ComponentFragment : FrameBaseFragment<EmptyViewModel>() {

    override fun getLayoutId(): Int = R.layout.main_fragment_component

    override fun initView() {}

    override fun setListener() {
        funModuleTv.setOnClickListener {
            Router.goPage(Page.MainModule.FUN_MODULE_LIST)
        }

        busModuleTv.setOnClickListener {
            Router.goPage(Page.MainModule.BUS_MODULE_LIST)
        }
    }
}