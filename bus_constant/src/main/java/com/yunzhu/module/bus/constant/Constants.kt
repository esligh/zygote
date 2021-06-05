package com.yunzhu.module.bus.constant

/**
 * @author：lisc
 * @desc：与业务有关的一些配置常量，这些常量一般不会很多，比如交易状态，交易类型等
 * */
object Constants
{
    /**
     * 服务端通用响应状态status
     */
    object CommonStatus {
        const val SYS_WAIT = -1 //系统处理等待
        const val UN_PROCESSED = 0
        const val SUCCESS = 1
        const val FAIL = 2
        const val IN_PROCESSED = 3
    }

    object KeyAlias
    {
        const val MASTER_KEY = "master_key"
    }
}