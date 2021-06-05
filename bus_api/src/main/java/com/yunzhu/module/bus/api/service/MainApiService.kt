package com.yunzhu.module.bus.api.service

import com.yunzhu.module.bus.api.entity.BaseResponse
import com.yunzhu.module.bus.model.api.common.Session
import retrofit2.http.*

/**
 * @author: lisc
 * @date: 2019-12-12
 * @desc:定义公共的api服务接口
 * */
interface MainApiService {

    /**
     * @description: 登录
     * @param: 接口参数
     * @return:
     */
    @FormUrlEncoded
    @POST
    suspend fun login(@Url url: String,@FieldMap params:Map<String,String>): BaseResponse<Session>

}
