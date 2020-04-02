package com.lzk.wanandroidkotlin.ui.wx

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/28
 * Function:
 */
interface WxChildContract {
    interface View: IBaseView{
        fun requestWxArticleSuccess(pageCount: Int,data: MutableList<ArticleBean>)
        fun requestCollectResult(isSuccess: Boolean,msg: String)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String)
    }

    interface Presenter{
        fun requestWxArticle(pageNum: Int,id: Int)
        fun requestCollect(position: Int,articleId: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}