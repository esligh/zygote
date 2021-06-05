package com.yunzhu.module.bus.api.service

import com.yunzhu.module.bus.api.entity.BaseResponse
import retrofit2.http.*

/**
 * @author: lisc
 * @date: 2019-12-12
 * @desc:定义公共的api服务接口
 * */
interface CommonApiService {

    /**
     * @description: 请求短信验证码
     * @param: 接口参数
     * @return:
     */
    @GET
    suspend fun reqSmsCode(@Url url:String,@QueryMap params:Map<String,String>): BaseResponse<String>


    /**
     * @description: 验证短信验证码
     * @param: 接口参数
     * @return:
     */
    @FormUrlEncoded
    @POST
    suspend fun smsCodeVerify(@Url url:String,@FieldMap params:Map<String,String>): BaseResponse<String>


}
