package com.lzk.wanandroidkotlin.ui.me

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
class MePresenter : BasePresenter<MeContract.View>(),MeContract.Presenter {

    companion object{
        const val TYPE_LOGOUT = 1
    }

    override fun requestIntegral() {
        RetrofitUtil.getService().getIntegral().enqueue(object : Callback<BaseBean<IntegralBean>>{
            override fun onFailure(call: Call<BaseBean<IntegralBean>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<IntegralBean>>,
                response: Response<BaseBean<IntegralBean>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        getView()?.requestIntegralSuccess(result.data)
                    }else{
                        getView()?.showError(result.errorMsg)
                    }
                }
            }

        })
    }

    override fun requestLogout() {
        RetrofitUtil.getService().getLogout().enqueue(object : Callback<BaseBean<UserBean>>{
            override fun onFailure(call: Call<BaseBean<UserBean>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<UserBean>>,
                response: Response<BaseBean<UserBean>>
            ) {
                val result = response.body()
                if (result!!.isSuccess()){
                    getView()?.requestLogoutSuccess()
                }else{
                    getView()?.showError(result.errorMsg)
                }
            }

        })
    }
}