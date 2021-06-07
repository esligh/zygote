package com.yunzhu.module.bus.api.exception

import java.lang.RuntimeException

class ServerException(var status: Int, var errorCode: String?, var errorMsg: String?) :
    RuntimeException()