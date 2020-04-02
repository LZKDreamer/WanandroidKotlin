package com.lzk.wanandroidkotlin.ui.collect

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.CollectBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
interface CollectContract {
    interface View: IBaseView{
        fun requestCollectListSuccess(pageCount: Int,data: MutableList<CollectBean>)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String,position: Int)
    }

    interface Presenter{
        fun requestCollectList(page: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}