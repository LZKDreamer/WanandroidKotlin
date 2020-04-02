package com.lzk.wanandroidkotlin.ui.user

import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
interface LoginContract {
    interface View: IBaseView{
        fun requestLoginSuccess(data: UserBean)
    }

    interface Presenter{
        fun requestLogin(username: String,password: String)
    }
}