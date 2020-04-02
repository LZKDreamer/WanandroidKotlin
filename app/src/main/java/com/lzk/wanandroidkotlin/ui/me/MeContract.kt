package com.lzk.wanandroidkotlin.ui.me

import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
interface MeContract {
    interface View: IBaseView{
        fun requestIntegralSuccess(data: IntegralBean)
        fun requestLogoutSuccess()
    }

    interface Presenter{
        fun requestIntegral()
        fun requestLogout()
    }
}