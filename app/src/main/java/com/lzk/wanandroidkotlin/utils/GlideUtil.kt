package com.lzk.wanandroidkotlin.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
object GlideUtil {
    fun loadImage(context: Context,url: String?,imageView: ImageView){
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.default_project_img)
            .centerCrop()
            .into(imageView);
    }
}