package com.lzk.wanandroidkotlin.ui.tree.system

import com.lzk.wanandroidkotlin.api.model.SystemBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
interface SystemContract {
    interface View: IBaseView{
        fun requestSystemSuccess(data: MutableList<SystemBean>)
    }

    interface Presenter{
        fun requestSystem()
    }
}