package com.yunzhu.module.common.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * @author: lisc
 * @desc: ImageView的简单拓展
 * */

fun ImageView.load(url: String?) {
    url?.let {
        var requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(ColorDrawable(Color.TRANSPARENT))
            .error(ColorDrawable(Color.TRANSPARENT))
            .centerCrop()

        Glide.with(this.context)
            .load(url)
            .apply(requestOptions)
            .into(this)
    }
}

fun ImageView.load(url:String?,options:RequestOptions)
{
    url?.let {
        Glide.with(this.context)
            .load(url)
            .apply(options)
            .into(this)
    }
}

fun ImageView.load(url: String?, holder: Int) {
    url?.let {
        var requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(holder)
            .error(ColorDrawable(Color.TRANSPARENT))
            .centerCrop()

        Glide.with(this.context)
            .load(url)
            .apply(requestOptions)
            .into(this)
    }
}

fun ImageView.load(url:String?,roundCorners: RoundedCorners)
{
    url?.let {
        var requestOptions = RequestOptions.bitmapTransform(roundCorners)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(ColorDrawable(Color.TRANSPARENT))
            .error(ColorDrawable(Color.TRANSPARENT))

        Glide.with(this.context)
            .load(url)
            .apply(requestOptions)
            .into(this)
    }
}

fun ImageView.load(url: Any) {

    var requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(ColorDrawable(Color.TRANSPARENT))
        .error(ColorDrawable(Color.TRANSPARENT))
        .centerCrop()

    Glide.with(this.context)
         .load(url)
         .apply(requestOptions)
         .into(this)
}

fun ImageView.load(url:Any,options:RequestOptions)
{
    Glide.with(this.context)
        .load(url)
        .apply(options)
        .into(this)
}

fun ImageView.load(url: Any, holder: Int) {
    var requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(holder)
        .error(ColorDrawable(Color.TRANSPARENT))
        .centerCrop()

    Glide.with(this.context)
         .load(url)
         .apply(requestOptions)
         .into(this)
}

fun ImageView.load(url:Any,roundCorners: RoundedCorners)
{
    var requestOptions = RequestOptions.bitmapTransform(roundCorners)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(ColorDrawable(Color.TRANSPARENT))
        .error(ColorDrawable(Color.TRANSPARENT))

    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}
