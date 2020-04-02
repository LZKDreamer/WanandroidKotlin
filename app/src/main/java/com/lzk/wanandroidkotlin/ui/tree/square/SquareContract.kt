package com.lzk.wanandroidkotlin.ui.tree.square

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
interface SquareContract {
    interface View: IBaseView{
        fun requestSquareListSuccess(pageCount: Int,data: MutableList<ArticleBean>)
        fun requestCollectResult(isSuccess: Boolean,msg: String)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String)
    }

    interface Presenter{
        fun requestSquareList(page: Int)
        fun requestCollect(position: Int,articleId: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}