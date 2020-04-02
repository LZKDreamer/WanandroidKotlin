package com.lzk.wanandroidkotlin.ui.search

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
interface SearchResultContract {
    interface View: IBaseView{
        fun requestSearchSuccess(
            pageCount: Int,
            data: MutableList<ArticleBean>
        )
        fun requestCollectResult(isSuccess: Boolean,msg: String)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String)
    }

    interface Presenter{
        fun requestSearch(pageNum: Int,key: String)
        fun requestCollect(position: Int,articleId: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}