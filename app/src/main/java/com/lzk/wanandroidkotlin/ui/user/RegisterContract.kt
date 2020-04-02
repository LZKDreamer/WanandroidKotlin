package com.lzk.wanandroidkotlin.ui.user

import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.IBaseView

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/30
 * Function:
 */
interface RegisterContract {
    interface View: IBaseView{
        fun requestRegisterSuccess(data: UserBean)
    }

    interface Presenter{
        fun requestRegister(username: String,password: String,repassword: String)
    }
}