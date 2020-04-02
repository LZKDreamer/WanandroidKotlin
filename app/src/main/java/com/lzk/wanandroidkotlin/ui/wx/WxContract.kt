package com.lzk.wanandroidkotlin.ui.wx

import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.CategoryBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/28
 * Function:
 */
interface WxContract {
    interface View: IBaseView{
        fun requestWxTabSuccess(data: MutableList<CategoryBean>)

    }

    interface Presenter{
        fun requestWxTab()
    }
}