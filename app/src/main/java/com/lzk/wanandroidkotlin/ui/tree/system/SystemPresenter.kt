package com.lzk.wanandroidkotlin.ui.tree.system

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.SystemBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class SystemPresenter : BasePresenter<SystemContract.View>(),SystemContract.Presenter{
    override fun requestSystem() {
        RetrofitUtil.getService().getSystem().enqueue(object : Callback<BaseBean<MutableList<SystemBean>>>{
            override fun onFailure(call: Call<BaseBean<MutableList<SystemBean>>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<SystemBean>>>,
                response: Response<BaseBean<MutableList<SystemBean>>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        if (result.data.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestSystemSuccess(result.data)
                        }
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }
}