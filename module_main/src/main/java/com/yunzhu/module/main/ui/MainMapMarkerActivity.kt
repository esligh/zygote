package com.yunzhu.module.main.ui

import android.os.Bundle
import android.os.PersistableBundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.model.LatLng
import com.yunzhu.module.`fun`.map.gaode.GaodeMapHelper
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_map_marker.*

@Route(path = Page.MainModule.MAP_MARKER)
class MainMapMarkerActivity : FrameBaseActivity<EmptyViewModel>() {

    override fun getLayoutId(): Int  = R.layout.main_activity_map_marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun initView() {
        title = "Map Marker"
        GaodeMapHelper.addMarkerToMap(mapView.map, LatLng(39.761, 116.434),R.drawable.icon_car)
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