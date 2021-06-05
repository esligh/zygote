package com.yunzhu.module.main.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.yunzhu.module.`fun`.map.gaode.GaodeMapHelper
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_map_my_location.*

@Route(path = Page.MainModule.MAP_MY_LOCATION)
class MainMapMyLocationActivity : FrameBaseActivity<EmptyViewModel>() {

    override fun getLayoutId(): Int  = R.layout.main_activity_map_my_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        GaodeMapHelper.locationMySelf(mapView,interval = 2000){
            Log.d("sss","lat="+it.latitude)
            Log.d("sss","long="+it.longitude)
        }
    }

    override fun initView() {
        title = "Map My Location"
    }

    override fun setListener() {

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}