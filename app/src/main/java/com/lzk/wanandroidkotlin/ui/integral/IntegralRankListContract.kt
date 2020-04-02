package com.lzk.wanandroidkotlin.ui.integral

import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
interface IntegralRankListContract {
    interface View: IBaseView{
        fun requestIntegralRankListSuccess(pageCount: Int,data: MutableList<IntegralBean>)
    }

    interface Presenter{
        fun requestIntegralRankList(pageNum: Int)
    }
}