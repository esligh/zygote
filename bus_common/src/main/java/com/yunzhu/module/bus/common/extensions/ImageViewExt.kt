package com.yunzhu.module.bus.common.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yunzhu.module.common.R

fun ImageView.load(url: Any) {

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(ColorDrawable(Color.WHITE))
        .error(ColorDrawable(Color.WHITE))
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
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(holder)
        .error(ColorDrawable(Color.WHITE))
        .centerCrop()

    Glide.with(this.context)
         .load(url)
         .apply(requestOptions)
         .into(this)
}

fun ImageView.load(url:Any,roundCorners: RoundedCorners)
{
    val requestOptions = RequestOptions.bitmapTransform(roundCorners)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(ColorDrawable(Color.WHITE))
        .error(ColorDrawable(Color.WHITE))

    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}
