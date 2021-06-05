package com.yunzhu.module.common.http

import com.orhanobut.logger.Logger
import com.yunzhu.module.common.utils.L
import com.yunzhu.module.config.AppConfig
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MyLoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //long t1 = System.nanoTime();//请求发起的时间
        val method = request.method()
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body() is FormBody) {
                val body = request.body() as FormBody?
                for (i in 0 until body!!.size()) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                if (sb.isNotEmpty()) {
                    sb.delete(sb.length - 1, sb.length)
                }
                L.d(
                    AppConfig.TAG, String.format("post %s on %s %n%s",
                        request.url(), chain.connection(), request.headers() /*, sb.toString()*/
                    )
                )
            } else if (request.body() is RequestBody) {
                Logger.d("post on url ==> " + request.url())
            }
        } else {
            L.d(AppConfig.TAG, String.format("get %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()))
        }
        val response = chain.proceed(request)
        // long t2 = System.nanoTime();//收到响应的时间
        val responseBody = response.peekBody(1024 * 1024.toLong())
        val resultJson = responseBody.string()
        L.json(AppConfig.TAG, "response:", resultJson)
        return response
    }
}