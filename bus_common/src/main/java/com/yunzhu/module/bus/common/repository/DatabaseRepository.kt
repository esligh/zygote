package com.yunzhu.module.bus.common.repository

import com.yunzhu.module.bus.db.AppDatabase
import com.yunzhu.module.bus.db.dao.UserDao
import com.yunzhu.module.common.base.BaseApplication


/**
 * @author: lisc
 * @date: 2021-01-05 上午 11:20
 * @desc: db组件的仓库服务 -- 与业务的隔离层 所有业务模块/组件应该使用这一层来访问数据库
 */
object DatabaseRepository
{
    fun userDao(): UserDao
    {
        return AppDatabase.instance(BaseApplication.instance).userDao()
    }

}