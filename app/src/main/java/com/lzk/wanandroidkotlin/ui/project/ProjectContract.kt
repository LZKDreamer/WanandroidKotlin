package com.lzk.wanandroidkotlin.ui.project

import com.lzk.wanandroidkotlin.api.model.CategoryBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
interface ProjectContract {
    interface View: IBaseView{
        fun requestProjectCategorySuccess(data: MutableList<CategoryBean>)
    }

    interface Presenter{
        fun requestProjectCategory()
    }
}