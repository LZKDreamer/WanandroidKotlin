package com.lzk.wanandroidkotlin.ui.integral

import com.lzk.wanandroidkotlin.api.model.IntegralHistoryBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
interface IntegralHistoryContract {
    interface View: IBaseView{
        fun requestIntegralHistorySuccess(pageCount: Int,data: MutableList<IntegralHistoryBean>)
    }

    interface Presenter{
        fun requestIntegralHistory(pageNum: Int)
    }
}