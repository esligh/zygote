package com.yunzhu.module.common.http

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yunzhu.module.common.BuildConfig
import com.yunzhu.module.common.base.BaseApplication.Companion.instance
import com.yunzhu.module.common.config.Config
import com.yunzhu.module.common.entity.ResponseWithJson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory private constructor() {

    private val retrofit: Retrofit
    private val client: OkHttpClient
    private val cookieJar: ClearableCookieJar

    private fun createGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(String::class.java, StringDefaultAdapter())
        builder.registerTypeAdapter(ResponseWithJson::class.java, JSONResultType())
        return builder.create()
    }

    private object SingletonHolder {
        val instance = ApiFactory()
    }

    fun <T> create(service: Class<T>?): T {
        return retrofit.create(service)
    }


    fun getRetrofit(): Retrofit {
        return retrofit
    }

    fun getClient(): OkHttpClient? {
        return client
    }

    fun getCookieJar(): ClearableCookieJar? {
        return cookieJar
    }

    companion object {
        private const val DEFAULT_TIME_OUT: Long = 10 //timeout time
        private const val DEFAULT_READ_TIME_OUT: Long = 10
        private const val BASE_URL = "https://me.com.cn"
        fun instance(): ApiFactory {
            return SingletonHolder.instance
        }
    }

    init {
        cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(instance)
        )
        val builder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .sslSocketFactory(
                SSLSocketClient.getSSLSocketFactory(),
                SSLSocketClient.getTrustManager()
            )
        if (BuildConfig.BUILD_TYPE.equals(Config.BuildType.DEBUG, ignoreCase = true) ||
            BuildConfig.BUILD_TYPE.equals(Config.BuildType.TEST, ignoreCase = true)) {
            builder.addInterceptor(MyLoggingInterceptor())
        }
        client = builder.build()
        retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .baseUrl(BASE_URL)
            .build()
    }


}