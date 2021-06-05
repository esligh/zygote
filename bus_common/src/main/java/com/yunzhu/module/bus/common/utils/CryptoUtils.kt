package com.yunzhu.module.bus.common.utils

import com.yunzhu.module.bus.model.api.common.Key
import com.yunzhu.module.common.crypto.base.SM2Strategy
import com.yunzhu.module.common.crypto.base.SM4Strategy

/**
 * @author:lisc
 * @date:2019-11-12
 */
class CryptoUtils
{
    companion object {

        fun getFlatKey(dataSet:List<String>,smKey: Key?):String
        {
            var result = ""
            smKey?.apply {
                val keys = StringBuilder()
                dataSet.forEach {
                    val enStr = SM4Strategy.getDefault().sm4EncryptECB(it,smKey.sm4KeyValue)
                    keys.append(enStr)
                }
                result = SM2Strategy.getDefault().sm2Encrypt(smKey.sm2Key, keys.toString())
            }
            return result
        }

        fun getFlatKey(key:String?,smKey: Key?):String
        {
            var result = ""
            smKey?.apply {
                val keys = StringBuilder()
                key?.forEach {
                    val s = it.toString()
                    val data = s+"000000000000000"
                    val enStr = SM4Strategy.getDefault().sm4EncryptECB(data,smKey.sm4KeyValue)
                    keys.append(enStr)
                }

                result = SM2Strategy.getDefault().sm2Encrypt(smKey.sm2Key, keys.toString())
            }
            return result
        }
    }

}
