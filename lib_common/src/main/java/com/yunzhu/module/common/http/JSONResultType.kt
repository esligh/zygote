package com.yunzhu.module.common.http

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.yunzhu.module.common.entity.ResponseWithJson
import org.json.JSONObject
import java.lang.reflect.Type

//解析类
class JSONResultType : JsonDeserializer<ResponseWithJson> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ResponseWithJson {
        return if (json.isJsonObject) {
            var response = ResponseWithJson()
            val obj = json.asJsonObject
            response.status = obj.get("status").asInt
            val code = obj.get("errorCode")
            if(code.isJsonNull){
                response.errorCode = ""
            }else{
                response.errorCode = code.asString
            }

            var errMsg = obj.get("failureDetails")
            if(errMsg.isJsonNull){
                response.errorMsg = ""
            }else{
                response.errorMsg = errMsg.asString
            }
            val data = obj.getAsJsonObject("data")
            if(data.isJsonNull){
                response.data = JSONObject()
            }else{
                response.data = JSONObject(data.toString())
            }
            response
        } else {
            ResponseWithJson()
        }
    }
}