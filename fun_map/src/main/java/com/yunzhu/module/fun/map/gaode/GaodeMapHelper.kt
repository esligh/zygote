package com.yunzhu.module.`fun`.map.gaode

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.text.TextUtils
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.route.RouteSearch
import com.amap.api.services.route.RouteSearch.*


/**
 * @author: lisc
 * @date: 2021-03-04 下午 03:53
 * @desc: 高德地图帮助类
 */
object GaodeMapHelper
{
    /**
     * 定位自身位置
     * @param mapView  地图对象
     * @param interval 刷新频率 默认1s一次
     * */
    fun locationMySelf(mapView:MapView,type:Int = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE,
                       interval:Long=1000L,zoomTo:Float = 15f,
                       onLocationChanged:(Location)->Unit = {})
    {
        val aMap = mapView.map
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(interval) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true)
        myLocationStyle.myLocationType(type)
        myLocationStyle.strokeColor(Color.TRANSPARENT)//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(Color.TRANSPARENT)//设置定位蓝点精度圆圈的填充颜色的方法。
        /*
        type 类型参考以下：
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        */
        //初始化地图控制器对象
        aMap.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        aMap.uiSettings.isMyLocationButtonEnabled = true
        aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        aMap.showIndoorMap(true)
        aMap.setOnMyLocationChangeListener {
            onLocationChanged(it)
        }
        //缩放级别，从 3 到 19。数字越大，展示的图面信息越精细
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomTo))
    }

    /**
     * 获取位置信息
     * @param purpose
     * @param bOnce 默认获取一次
     * @param onLocationListener
     * */
    fun listenLocation(appContext: Context, purpose: AMapLocationClientOption.AMapLocationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn,
                       bOnce:Boolean = true, interval: Long = 1000,onLocationListener: (AMapLocation) -> Unit):AMapLocationClient
    {
        //声明AMapLocationClient类对象
        var locationClient = AMapLocationClient(appContext)
        val option = AMapLocationClientOption()
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        /**
         * 高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
         * 低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
         * 仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，需要在室外环境下才可以成功定位。
         * 注意，自 v2.9.0 版本之后，仅设备定位模式下支持返回地址描述信息。
         * */
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //SDK默认采用连续定位模式，时间间隔2000ms。如果您需要自定义调用间隔
        option.isOnceLocation = bOnce
        //设置是否返回地址信息（默认返回地址信息）
        option.isNeedAddress = true
        option.interval = interval

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        option.httpTimeOut = 20000
        //关闭缓存机制
        option.isLocationCacheEnable = false
        //设置定位回调监听
        locationClient.setLocationListener {
            onLocationListener(it)
        }
        if(null != locationClient){
            locationClient.setLocationOption(option)
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            locationClient.stopLocation()
            locationClient.startLocation()
        }
        return locationClient
    }

    //添加标记到地图中
    fun addMarkerToMap(aMap: AMap, latlng: LatLng, drawableRes:Int,
                       title:String="",snippet:String=""):Marker
    {
        val markerOption =
            MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(drawableRes))
            .position(latlng)
            .draggable(true)
        if (!TextUtils.isEmpty(title)) {
            markerOption.title(title)
        }
        if(!TextUtils.isEmpty(snippet)){
            markerOption.snippet(snippet)
        }
        return aMap.addMarker(markerOption)
    }

    /**
     * 路径规划查询
     * */
    fun routeSearch(routeSearch:RouteSearch,from: LatLonPoint, to:LatLonPoint,routeType:Int,cityName:String="")
    {
        val fromAndTo = FromAndTo(from,to)

        when(routeType){
            MapConstants.ROUTE_TYPE_RIDE -> {
                // 异步路径规划骑行模式查询
                val query = RideRouteQuery(fromAndTo, RouteSearch.RidingDefault)
                routeSearch.calculateRideRouteAsyn(query)
            }

            MapConstants.ROUTE_TYPE_WALK -> {
                // 异步路径规划步行模式查询
                val query = WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault)
                routeSearch.calculateWalkRouteAsyn(query)
            }

            MapConstants.ROUTE_TYPE_DRIVE -> {
                // 驾车路径规划
                // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                val query = DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "")
                routeSearch.calculateDriveRouteAsyn(query) // 异步路径规划驾车模式查询
            }

            MapConstants.ROUTE_TYPE_BUS -> {
                // 公交路径规划
                // 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
                val query = BusRouteQuery(fromAndTo, RouteSearch.BusDefault, cityName, 0)
                routeSearch.calculateBusRouteAsyn(query) // 异步路径规划公交模式查询
            }
        }
    }

}