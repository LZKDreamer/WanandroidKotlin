package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
data class ArticlesPageBean<T>(
    val curPage: Int,
    val datas: T,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
): Serializable