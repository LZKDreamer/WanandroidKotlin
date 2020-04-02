package com.lzk.wanandroidkotlin.api.model

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
data class NavigationBean(
    val articles: MutableList<ArticleBean>,
    val cid: Int,
    val name: String
) {
}