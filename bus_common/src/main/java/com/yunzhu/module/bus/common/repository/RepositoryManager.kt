package com.yunzhu.module.bus.common.repository

import com.yunzhu.module.bus.api.repository.common.CommonApiRepository
import com.yunzhu.module.bus.api.repository.main.MainApiRepository
import com.yunzhu.module.bus.api.service.*
import com.yunzhu.module.common.http.ApiFactory

/**
 * @author: lisc
 * @date: 2019-12-12
 * @desc:api仓库管理器，组件开发者应该在此添加自己的api仓库服务,数据库仓库服务，本地存储仓库服务等
 */
object RepositoryManager {

    //Api管理对象
    val mainApiService: MainApiRepository
        get() = MainApiRepository.getInstance(ApiFactory.instance().create(MainApiService::class.java))

    val commonApiService: CommonApiRepository
        get() = CommonApiRepository.getInstance(ApiFactory.instance().create(CommonApiService::class.java))

    //数据库的管理对象
    val dbManager:DatabaseRepository  = DatabaseRepository
}
