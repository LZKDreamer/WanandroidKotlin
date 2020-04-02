package com.lzk.wanandroidkotlin.api.model

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
data class IntegralHistoryBean(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)