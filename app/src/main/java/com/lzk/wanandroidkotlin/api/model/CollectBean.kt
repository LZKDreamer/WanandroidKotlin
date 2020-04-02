package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
data class CollectBean(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
): Serializable