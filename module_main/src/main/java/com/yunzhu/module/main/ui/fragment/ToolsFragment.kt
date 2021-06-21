package com.yunzhu.module.main.ui.fragment

import com.yunzhu.module.bus.common.base.FrameBaseFragment
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.extensions.Router
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_fragment_tools.*

class ToolsFragment : FrameBaseFragment<EmptyViewModel>() {

    override fun getLayoutId(): Int  = R.layout.main_fragment_tools

    override fun initView() {

    }

    override fun setListener() {
        behaviorTv.setOnClickListener {
            Router.goPage(Page.MainModule.BEHAVIOR)
        }
    }
}