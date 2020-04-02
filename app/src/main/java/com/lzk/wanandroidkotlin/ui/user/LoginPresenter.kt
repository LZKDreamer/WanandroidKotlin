package com.lzk.wanandroidkotlin.ui.user

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
class LoginPresenter : BasePresenter<LoginContract.View>(),LoginContract.Presenter {

    override fun requestLogin(username: String, password: String) {
        RetrofitUtil.getService().getLogin(username, password).enqueue(object : retrofit2.Callback<BaseBean<UserBean>>{
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
                        getView()?.requestLoginSuccess(result.data)
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }
}