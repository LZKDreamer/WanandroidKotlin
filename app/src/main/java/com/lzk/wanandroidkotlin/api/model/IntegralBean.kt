package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable
import java.security.SecureRandom

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
data class IntegralBean(
    val coinCount: Int,
    val level: Int,
    val rank: Int,
    val userId: Int,
    val username: String
): Serializable