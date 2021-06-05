package com.yunzhu.lib_api

import org.junit.Test

import org.junit.Assert.*
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val u = URL("https://tieba.baidu.com/index.html")
        val result = String.format("%s://%s:%s",u.protocol,u.host,u.port)
        println(result)
        val u1 = URL("http://172.16.5.115:8080/zentaopms/www/my/")
        val result1 = String.format("%s://%s:%s",u1.protocol,u1.host,u1.port)
        println(result1)
    }
}
