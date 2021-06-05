package com.yunzhu.module.common.crypto.base

import com.yunzhu.module.common.security.totp.GoogleAuthenticator
import com.yunzhu.module.common.security.totp.GoogleAuthenticatorConfig
import com.yunzhu.module.common.security.totp.HmacHashFunction
import org.apache.commons.codec.binary.Base32
import org.junit.Before
import org.junit.Test
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author: lisc
 * @date: 2020-05-13 下午 04:07
 * @desc:
 */
class SM4StrategyTest {

    @Before
    @Throws(Exception::class)
    fun setUp() {}

    @Test
    fun testSm4DecryptECB() {
//        val key = "ffffffffd796ec44ffffffff868f8770"
////      val data = "410|RBDO6XLP57SORZEU"
////      println("content:"+data)
////      println("key:"+key)
////      val s = SM4Strategy.getDefault().sm4EncryptECBPadding(data,key)
////      println("encode data:"+s)
//        var s = "49E66F713F300DDB1CD1130F21762789E35C10FAD60F440166D8CB555542348E"
//        val d2 = SM4Strategy.getDefault().sm4DecryptECBPadding(s,key)
//        println("decode data:"+d2)

//        val strOriginalPassword = "990041206841499"
//        val afterSection = strOriginalPassword.substring(strOriginalPassword.length - 8)
//        println("afterSection:"+afterSection)
//        val beforSection = strOriginalPassword.substring(0, strOriginalPassword.length - 8)
//        println("beforSection:"+beforSection)
//
        val codec32 = Base32()
        val token = "BNNZKWQCETJQHMGH"
//        val l = token.toByteArray()
//        println("len="+l.size)
//        val s = codec32.decode(token.toUpperCase())
//        println("size="+s.size)
//        for(element in s) {
//            println(element)
//        }
//
//        val s1 = codec32.encode(s)
//        s1.forEach {
//            print(it)
//        }
//
//
//        println()
//        val s2 = token.toByteArray()
//        s2.forEach {
//            print(it)
//        }
//
//        val s3 =com.yunzhu.module.common.utils.Base32.decode(token)
//        for(element in s3) {
//            println(element)
//        }
//
//        val v = 13
//        val sss = v.ushr(2)
//        println(sss)

        val builder = GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
        builder.setHmacHashFunction(HmacHashFunction.HmacSHA512)
//        builder.setWindowSize(10)
        val auth = GoogleAuthenticator(builder.build())

        val dynamic = auth.getTotpPassword(token,1593670944439L)
        println(dynamic) //421371

        println(1593670944439L/30000)

//        println(dynamic)

//        val id = 10000506L
//        val type = 0
//        val dynamic = 652809
//        println(id*99)
//        val s = (id * 99 + type) * 999999L
//        println(s)
//
//        val max = Long.MAX_VALUE
//        println(max)

//        val beforeSectionStr = "09900491"
//        val afterSectionStr = "04602715"
//        val result = xor(beforeSectionStr,afterSectionStr)
//        println(result)


    }


    private fun xor(beforeStr: String, afterStr: String): String {
        val beforeChars = beforeStr.toCharArray()
        val afterChars = afterStr.toCharArray()
        //      logger.info("前八数组：[{}]，后八数组：[{}]", beforChars, afterChars);
        val xorStr = StringBuffer()
        for (i in 0..7) {
            val beforeNum = Integer.valueOf(beforeChars[i] + "")
            val afterNum = Integer.valueOf(afterChars[i] + "")
            var xorNum = 0
            if (beforeNum == 8 || beforeNum == 9 || afterNum == 8 || afterNum == 9) {
                xorNum = beforeNum
            } else {
                xorNum = beforeNum xor afterNum
            }
            xorStr.append(xorNum)
        }
        return xorStr.toString()
    }
}