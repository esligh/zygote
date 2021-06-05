package com.yunzhu.module.bus.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yunzhu.module.bus.model.db.User
import io.reactivex.Flowable

/**
 * @author: lisc
 * @date: 2020-10-26 下午 07:43
 * @desc:
 */
@Dao
interface UserDao
{
    @Query("SELECT * FROM User")
    fun getUsers(): Flowable<List<User>>

    @Query("SELECT * FROM User where id = :id" )
    suspend fun getUserById(id:Long): User?

    @Insert
    suspend fun insert(vararg users: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}