package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
data class UserBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
): Serializable