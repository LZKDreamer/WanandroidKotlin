package com.lzk.wanandroidkotlin.api.model

import org.litepal.crud.LitePalSupport

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
class SearchHistoryBean : LitePalSupport() {
    val id: Int = 0
    var key: String = ""
    var date: Long = 0
}