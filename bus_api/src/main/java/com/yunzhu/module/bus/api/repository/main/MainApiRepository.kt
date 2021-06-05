package com.yunzhu.module.bus.api.repository.main

import com.yunzhu.module.bus.api.config.URL
import com.yunzhu.module.bus.api.entity.BaseResponse
import com.yunzhu.module.bus.api.repository.base.BaseApiRemoteSource
import com.yunzhu.module.bus.api.service.MainApiService
import com.yunzhu.module.bus.model.api.common.Session

/**
 * @author:lisc
 * @date:2019-10-16
 * @desc：公共api的仓库服务实现
 */
class MainApiRepository(private val mApi: MainApiService): BaseApiRemoteSource() {
    companion object {
        @Volatile
        private var sInstance: MainApiRepository? = null

        fun getInstance(service:MainApiService): MainApiRepository {
            if (sInstance == null) {
                synchronized(MainApiRepository::class) {
                    if (sInstance == null) {
                        sInstance = MainApiRepository(service)
                    }
                }
            }
            return sInstance!!
        }
    }

    suspend fun login(params: Map<String, String>): BaseResponse<Session> {
        var apiParams:MutableMap<String,String> = publicParams().toMutableMap()
        apiParams["phone"] = ( params["phone"] ?:"")
        apiParams["password"] = ( params["password"] ?:"")
        apiParams
        checkParams(apiParams)
        return requestApi { mApi.login(URL.BASE_SERVER_URL+URL.BASE_PREFIX+URL.Main.LOGIN,apiParams) }
    }

}