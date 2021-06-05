package com.yunzhu.module.common.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.alibaba.android.arouter.launcher.ARouter
import java.io.Serializable


/**
 * @author:lisc
 * @date: 2019-06-10
 * @description: 路由管理器
 */
object Router {

    //单页面跳转路由
    fun goPage(page:String){
        ARouter.getInstance().build(page).navigation()
    }


    //带单个Int值的页面路由
    fun goPage(page:String,key:String,value:Int)
    {
        ARouter.getInstance().build(page)
            .withInt(key,value)
            .navigation()
    }

    //带单个String值的页面路由
    fun goPage(page:String,key:String,value:String?)
    {
        ARouter.getInstance().build(page)
            .withString(key,value)
            .navigation()
    }

    //带单个Parcel值的页面路由
    fun goPage(page:String,key:String,value: Parcelable?)
    {
        ARouter.getInstance().build(page)
            .withParcelable(key,value)
            .navigation()
    }

    //带单个Serializable值的页面路由
    fun goPage(page:String,key:String,value: Serializable?)
    {
        ARouter.getInstance().build(page)
            .withSerializable(key, value)
            .navigation()
    }

    //带单个Boolean值的页面路由
    fun goPage(page:String,key:String,value:Boolean)
    {
        ARouter.getInstance().build(page)
            .withBoolean(key,value)
            .navigation()
    }

    fun goPage(page:String,bundle:Bundle){
        ARouter.getInstance().build(page)
            .with(bundle)
            .navigation()
    }

    fun goPage(page:String,key:String,bundle:Bundle){
        ARouter.getInstance().build(page)
            .withBundle(key,bundle)
            .navigation()
    }

    fun goPageForResult(activity:Activity,page:String,requestCode:Int)
    {
        ARouter.getInstance().build(page).navigation(activity,requestCode)
    }

    fun goPageForResult(activity:Activity,page:String,requestCode:Int, key: String, bValue: Boolean) {
        ARouter.getInstance().build(page)
            .withBoolean(key, bValue)
            .navigation(activity,requestCode)
    }

    fun goPageForResult(activity:Activity,page:String,requestCode:Int, key: String, value: String) {
        ARouter.getInstance().build(page)
            .withString(key, value)
            .navigation(activity,requestCode)
    }

    fun goPageForResult(activity:Activity,page:String,requestCode:Int, key: String, value: Parcelable?) {
        ARouter.getInstance().build(page)
            .withParcelable(key,value)
            .navigation(activity,requestCode)
    }

    fun<T : Parcelable> goPageForResult(activity:Activity,page:String,requestCode:Int,
                                        key1: String, bValue1: Boolean,
                                        key2: String, value2: ArrayList<T>) {
        ARouter.getInstance().build(page)
            .withBoolean(key1, bValue1)
            .withParcelableArrayList(key2, value2)
            .navigation(activity,requestCode)
    }

    fun<T : Parcelable> goPageForResult(activity:Activity,page:String,requestCode:Int,
                                        key: String, value: ArrayList<T>?) {
        ARouter.getInstance().build(page)
            .withParcelableArrayList(key, value)
            .navigation(activity,requestCode)
    }
}