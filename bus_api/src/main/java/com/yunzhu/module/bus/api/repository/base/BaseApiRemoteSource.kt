package com.yunzhu.module.bus.api.repository.base

import android.text.TextUtils
import com.google.gson.Gson
import com.yunzhu.module.base.tools.utils.L
import com.yunzhu.module.bus.api.entity.BaseResponse
import com.yunzhu.module.bus.api.exception.ExceptionEngine
import com.yunzhu.module.bus.api.exception.ServerException
import com.yunzhu.module.bus.constant.Constants
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

open class BaseApiRemoteSource
{
    /**
     * @description: 公共参数
     * @param:
     * @return:
     */
    fun publicParams(): Map<String, String> {
        return HashMap()
    }

    /**
     * @description: 参数检查
     * @param:
     * @return:
     */
    fun checkParams(params: Map<String, Any>?): Boolean {
        if (params == null) {
            return false
        }
        L.d("提交参数：\n")
        L.d("==================================================\n")
        val keySet = params.keys
        val keys = ArrayList(keySet)
        keys.sort()
        val urlParams = StringBuilder()
        for (key in keys) {
            if (!TextUtils.isEmpty(params[key].toString())) {
                urlParams.append(key)
                    .append("=")
                    .append(params[key])
                urlParams.append("&")
                L.d("║  \"" + key + "\":\"" + params[key] + "\"")
            }
        }
        var paramStr = ""
        if(!TextUtils.isEmpty(urlParams)){
            paramStr = urlParams.subSequence(0, urlParams.length - 1).toString()
        } else {
            L.d("║  \"")
            L.d("║  \"")
            L.d("║  \"")
        }
        L.d( "==================================================\n")
        L.d("commit params str : $paramStr")
        return true
    }

    fun checkParams(params: JSONObject):Boolean
    {
        if (params == null) {
            return false
        }
        L.d("提交参数：\n")
        L.d("==================================================\n")
        val keyIter = params.keys()

        val keys = ArrayList<String>()
        while(keyIter.hasNext()){
            keys.add(keyIter.next())
        }
        keys.sort()
        val urlParams = StringBuilder()
        for (key in keys) {
            if (!TextUtils.isEmpty(params[key].toString())) {
                urlParams.append(key)
                    .append("=")
                    .append(params[key])
                urlParams.append("&")
                L.d("║  \"" + key + "\":\"" + params[key] + "\"")
            }
        }
        var paramStr = ""
        if(!TextUtils.isEmpty(urlParams)){
            paramStr = urlParams.subSequence(0, urlParams.length - 1).toString()
        } else {
            L.d("║  \"")
            L.d("║  \"")
            L.d("║  \"")
        }
        L.d( "==================================================\n")
        return true
    }

    /**
     * @description: 参数转换
     * @param:
     * @return:
     */
    fun convertToRequestBody(params:Map<String,Any>):RequestBody{
        var body = Gson().toJson(params)
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body)
    }

    /**
     * 网络请求，基于协程
     * */
    suspend fun <T : Any> requestApi(
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> {
        var baseResp = BaseResponse<T>()
        try {
            baseResp = block.invoke()
            if (baseResp.status != Constants.CommonStatus.SUCCESS) {
                throw ServerException(baseResp.status, baseResp.errorCode, baseResp.errorMsg)
            }
        } catch (e: Exception) {
            baseResp.error = ExceptionEngine.handleException(e)
        }
        return baseResp
    }

}
