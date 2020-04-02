package com.lzk.wanandroidkotlin.ui.tree.nav

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.NavigationBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class NavigationPresenter : BasePresenter<NavigationContract.View>(),NavigationContract.Presenter{
    override fun requestNavigation() {
        RetrofitUtil.getService().getNavigation().enqueue(object : Callback<BaseBean<MutableList<NavigationBean>>>{
            override fun onFailure(
                call: Call<BaseBean<MutableList<NavigationBean>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<NavigationBean>>>,
                response: Response<BaseBean<MutableList<NavigationBean>>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        if (result.data.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestNavigationSuccess(result.data)
                        }
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }
}