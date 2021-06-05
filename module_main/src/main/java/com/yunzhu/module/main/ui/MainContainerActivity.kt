package com.yunzhu.module.main.ui

import android.app.Activity
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.ExtraName
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.utils.ToastUtils
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_main.*

@Route(path = Page.MainModule.CONTAINER)
class MainContainerActivity : FrameBaseActivity<EmptyViewModel>() {

    override fun getLayoutId(): Int  = R.layout.main_activity_main

//    @Inject
//    lateinit var userRepository: UserRepository
//
//    fun test()
//    {
//        userRepository.getUsers()
//            .compose(RxTools.fIoMain())
//            .subscribe {
//                Log.d("sss","==========="+it.size)
//            }
//    }

    override fun initView() {
        val navController = findNavController(R.id.navHostFragment)
        navView.setupWithNavController(navController)
        title = getString(R.string.main_container_title)
    }

    override fun setListener() {
//        test()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            //Scan QR Code Result
            if (requestCode == 100) {
                val message = data?.getStringExtra(ExtraName.Common.QR_RESULT_CODE)
                ToastUtils.showShort(message)
            }
        }
    }
}