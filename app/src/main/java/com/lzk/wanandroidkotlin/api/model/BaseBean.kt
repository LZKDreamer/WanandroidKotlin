package com.lzk.wanandroidkotlin.api.model

import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
open class BaseBean<T>(var data: T,var errorCode: Int, var errorMsg: String): Serializable {
    fun isSuccess() = errorCode == 0
}