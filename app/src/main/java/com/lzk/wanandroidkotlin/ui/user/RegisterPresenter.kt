package com.lzk.wanandroidkotlin.ui.user

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/30
 * Function:
 */
class RegisterPresenter : BasePresenter<RegisterContract.View>(),RegisterContract.Presenter {
    override fun requestRegister(username: String, password: String, repassword: String) {
        RetrofitUtil.getService().getRegister(username, password, repassword).enqueue(object : Callback<BaseBean<UserBean>>{
            override fun onFailure(call: Call<BaseBean<UserBean>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<UserBean>>,
                response: Response<BaseBean<UserBean>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        getView()?.requestRegisterSuccess(result.data)
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }
}