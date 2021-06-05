package com.yunzhu.module.bus.common.vm

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.blankj.rxbus.RxBus
import com.yunzhu.module.bus.api.entity.BaseResponse
import com.yunzhu.module.bus.api.entity.DataState
import com.yunzhu.module.bus.common.repository.RepositoryManager
import com.yunzhu.module.bus.api.exception.ApiException
import com.yunzhu.module.bus.api.repository.base.StateLiveData
import com.yunzhu.module.common.common.RxEvent
import com.yunzhu.module.common.vm.BaseViewModel

/**
 * @author:Lee
 * @date:2020-04-16
 * @desc:ViewModel基类
 * */
abstract class FrameBaseViewModel(application: Application): BaseViewModel(application) {
    //全局异常监听
    var mDefaultApiException:MutableLiveData<ApiException> = MutableLiveData()
    val mApiRepoManager: RepositoryManager = RepositoryManager

    suspend fun<T> apiCall(block: suspend () -> BaseResponse<T>,
                           stateLiveData: StateLiveData<T>) {
        var baseResp = BaseResponse<T>()
        baseResp.dataState = DataState.STATE_LOADING
        stateLiveData.postValue(baseResp)

        val response = block.invoke()
        baseResp = response
        if(response.isSuccess){
            baseResp.dataState = DataState.STATE_SUCCESS
        }else{
            baseResp.dataState = DataState.STATE_FAILED
            val it = response.error
            if (it is ApiException) {
                mDefaultApiException.value = it
            }
        }
        stateLiveData.postValue(baseResp)
    }


    /**
     * @param:event
     * @desc: 发送全局事件通知，事件信息通过RxEvent对象承载
     * @see RxEvent
     * */
    open fun postEvent(event: RxEvent) {
        RxBus.getDefault().post(event)
    }

    /**
     * @param:eventCode 事件码 参见EventCode中的定义
     * @param:any 事件的负载数据
     * @desc: 发送全局事件通知，指定事件Code和数据
     * @see RxEvent , EventCode
     * */
    open fun postEvent(eventCode: Int,any:Any? = null)
    {
        postEvent(RxEvent(eventCode, any))
    }

    /**
     * @param tag 事件TAG
     * @param event 事件对象
     * @desc: 发送带tag的事件，该事件由注册该TAG的页面进行处理
     * */
    open fun postEventWithTag(tag:String,event: RxEvent)
    {
        RxBus.getDefault().post(event,tag)
    }

    open fun postEventWithTag(tag:String,event: Int,any:Any? = null)
    {
        postEventWithTag(tag,
            RxEvent(event, any)
        )
    }
}