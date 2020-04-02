package com.lzk.wanandroidkotlin.ui.tree.system

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/28
 * Function:
 */
interface SystemInfoContract {
    interface View: IBaseView{
        fun requestSystemInfoSuccess(pageCount: Int,data: MutableList<ArticleBean>)
    }

    interface Presenter{
        fun requestSystemInfo(pageNum: Int,id: Int)
    }
}