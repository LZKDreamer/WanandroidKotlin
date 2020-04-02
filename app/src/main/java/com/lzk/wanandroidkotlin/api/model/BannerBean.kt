package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
): Serializable
