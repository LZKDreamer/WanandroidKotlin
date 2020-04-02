package com.lzk.wanandroidkotlin.ui.project

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
interface ProjectChildContract {
    interface View: IBaseView{
        fun requestProjectsSuccess(pageCount: Int,data: MutableList<ArticleBean>)
        fun requestCollectResult(isSuccess: Boolean,msg: String)
        fun requestUnCollectResult(isSuccess: Boolean,msg: String)
    }

    interface Presenter{
        fun requestProjectList(page: Int,id: Int)
        fun requestCollect(position: Int,articleId: Int)
        fun requestUnCollect(position: Int,articleId: Int)
    }
}