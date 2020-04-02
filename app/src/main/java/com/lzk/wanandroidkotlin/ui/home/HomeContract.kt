package com.lzk.wanandroidkotlin.ui.home

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.BannerBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
interface HomeContract {
    interface View : IBaseView{
        fun requestBannerSuccess(data: MutableList<BannerBean>?)
        fun requestHomeTopArticleSuccess(data: MutableList<ArticleBean>?)
        fun requestHomeArticleSuccess(pageCount: Int,data: MutableList<ArticleBean>?)
        fun requestCollectResult(isSuccess: Boolean,msg: String)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String)
    }

    interface Presenter{
        fun requestBanner()
        fun requestHomeTopArticle()
        fun requestHomeArticle(pageNum: Int = 0)
        fun requestCollect(position: Int,articleId: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}