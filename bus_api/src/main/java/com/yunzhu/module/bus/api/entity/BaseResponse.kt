package com.yunzhu.module.bus.api.entity

import com.yunzhu.module.bus.api.exception.ApiException
import com.yunzhu.module.bus.constant.Constants

class BaseResponse<T> {
    var status = -1 // 返回的code
    var errorCode: String? = null
    var errorMsg: String? = null
    var data: T? = null
    var error: ApiException? = null
    var dataState: DataState? = null

    val isSuccess: Boolean
        get() = status == Constants.CommonStatus.SUCCESS
}


enum class DataState {
    STATE_CREATE,//创建
    STATE_LOADING,//加载中
    STATE_SUCCESS,//成功
    STATE_COMPLETED,//完成
    STATE_EMPTY,//数据为null
    STATE_FAILED,//接口请求成功但是服务器返回error
    STATE_UNKNOWN//未知
}