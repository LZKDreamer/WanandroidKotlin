package com.lzk.wanandroidkotlin.ui.search

import com.lzk.wanandroidkotlin.api.model.HotKeyBean
import com.lzk.wanandroidkotlin.api.model.SearchHistoryBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
interface SearchContract {
    interface View : IBaseView{
        fun requestHotKeySuccess(data: MutableList<HotKeyBean>)
        fun requestSearchHistorySuccess(data: List<SearchHistoryBean>?)
    }

    interface Presenter{
        fun requestHotKey()
        fun requestSearchHistory()
    }
}