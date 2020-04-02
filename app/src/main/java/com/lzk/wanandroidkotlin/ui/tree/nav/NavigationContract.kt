package com.lzk.wanandroidkotlin.ui.tree.nav

import com.lzk.wanandroidkotlin.api.model.NavigationBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
interface NavigationContract {
    interface View: IBaseView{
        fun requestNavigationSuccess(data: MutableList<NavigationBean>)
    }

    interface Presenter{
        fun requestNavigation()
    }
}