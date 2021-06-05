package com.yunzhu.module.main.ui

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocationClient
import com.yunzhu.module.`fun`.map.gaode.GaodeMapHelper
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.extensions.Router
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_main_map.*

/**
 * 地图组件功能首页
 * */
@Route(path = Page.MainModule.FUN_MAP)
class MainMapActivity : FrameBaseActivity<EmptyViewModel>() {

    var mapClient: AMapLocationClient? = null

    override fun getLayoutId(): Int  = R.layout.main_activity_main_map

    override fun initView() {
        title = "Map"
    }

    override fun setListener() {
        myLocationTv.setOnClickListener {
            Router.goPage(Page.MainModule.MAP_MY_LOCATION)
        }

        getLocationTv.setOnClickListener {
            mapClient = GaodeMapHelper.listenLocation(applicationContext,bOnce = false){
                Log.d("sss","my location,city="+it.city)
                Log.d("sss","lat="+it.latitude+",lang="+it.longitude)
            }
        }

        mapMarkerTv.setOnClickListener {
            Router.goPage(Page.MainModule.MAP_MARKER)
        }


        mapRouteTv.setOnClickListener {
            Router.goPage(Page.MainModule.MAP_ROUTE)
        }
    }

    override fun onStop() {
        super.onStop()
        mapClient?.stopLocation()
        mapClient?.onDestroy()
    }
}