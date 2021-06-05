package com.yunzhu.module.bus.api.repository.common

import com.yunzhu.module.bus.api.config.URL
import com.yunzhu.module.bus.api.entity.BaseResponse
import com.yunzhu.module.bus.api.repository.base.BaseApiRemoteSource
import com.yunzhu.module.bus.api.service.CommonApiService

/**
 * @author:lisc
 * @date:2019-10-16
 * @desc：公共api的仓库服务实现
 */
class CommonApiRepository(private val mApi: CommonApiService): BaseApiRemoteSource() {

    companion object {

        @Volatile
        private var sInstance: CommonApiRepository? = null

        fun getInstance(service:CommonApiService): CommonApiRepository{
            if (sInstance == null) {
                synchronized(CommonApiRepository::class) {
                    if (sInstance == null) {
                        sInstance = CommonApiRepository(service)
                    }
                }
            }
            return sInstance!!
        }
    }

    suspend fun reqSmsCode(params: Map<String, String>): BaseResponse<String> {
        var apiParams:MutableMap<String,String> = publicParams().toMutableMap()
        apiParams["phone"] = ( params["phone"] ?:"")
        apiParams["smsType"] = ( params["smsType"] ?:"")
        apiParams
        return requestApi { mApi.reqSmsCode(URL.BASE_SERVER_URL+URL.BASE_PREFIX+URL.Main.REQ_SMS_CODE,apiParams) }
    }


}