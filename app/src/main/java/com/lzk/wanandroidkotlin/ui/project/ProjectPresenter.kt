package com.lzk.wanandroidkotlin.ui.project

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.CategoryBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class ProjectPresenter: BasePresenter<ProjectContract.View>(),ProjectContract.Presenter{

    override fun requestProjectCategory() {
        RetrofitUtil.getService().getProjectCategory().enqueue(object : Callback<BaseBean<MutableList<CategoryBean>>>{
            override fun onFailure(
                call: Call<BaseBean<MutableList<CategoryBean>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<CategoryBean>>>,
                response: Response<BaseBean<MutableList<CategoryBean>>>
            ) {
                val result = response.body()
                if (result != null){
                    if (result.isSuccess()){
                        if (result.data.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestProjectCategorySuccess(result.data)
                        }
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }else{
                    getView()?.showEmpty()
                }
            }

        })
    }

}