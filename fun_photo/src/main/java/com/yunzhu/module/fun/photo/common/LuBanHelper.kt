package com.yunzhu.module.`fun`.photo.common

import android.content.Context
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import top.zibin.luban.OnRenameListener
import java.io.File

/**
 * @author: lisc
 * @date: 2020-10-22 下午 03:17
 * @desc:
 */
object LuBanHelper
{
    /**
     * 批量压缩图片
     * @param context
     * @param files 待压缩的文件列表
     * @param renameListener 压缩后的文件缓存名称
     * @param ignoreSize 文件大小小于该值则不进行压缩处理
     * */
    fun compressImages(context: Context, files:List<String>,targetDir:String,
        renameListener:OnRenameListener? = null,ignoreSize:Int=100)
        : Flowable<List<File>>
    {
        return Flowable.just(files)
            .observeOn(Schedulers.io())
            .map {
                val builder = Luban.with(context)
                    .load(it)
                    .ignoreBy(ignoreSize)
                    .setTargetDir(targetDir)
                if(renameListener != null){
                    builder.setRenameListener(renameListener)
                }
                builder.get()
            }.observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 压缩单张图片
     * */
    fun compressImage(context:Context,path:String,targetDir: String,
        renameListener:OnRenameListener? = null,
        onCompressListener: OnCompressListener,ignoreSize:Int=100)
    {
        Luban.with(context)
            .load(File(path))
            .ignoreBy(ignoreSize)
            .setTargetDir(targetDir)
            .setRenameListener(renameListener)
            .setCompressListener(onCompressListener)
            .launch()
    }

}