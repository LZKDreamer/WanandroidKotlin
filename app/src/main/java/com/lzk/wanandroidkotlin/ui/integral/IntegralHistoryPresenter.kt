package com.lzk.wanandroidkotlin.ui.integral

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.IntegralHistoryBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
class IntegralHistoryPresenter : BasePresenter<IntegralHistoryContract.View>(),IntegralHistoryContract.Presenter{

    override fun requestIntegralHistory(pageNum: Int) {
        RetrofitUtil.getService().getIntegralHistory(pageNum).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<IntegralHistoryBean>>>>{
            override fun onFailure(
                call: Call<BaseBean<ArticlesPageBean<MutableList<IntegralHistoryBean>>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<ArticlesPageBean<MutableList<IntegralHistoryBean>>>>,
                response: Response<BaseBean<ArticlesPageBean<MutableList<IntegralHistoryBean>>>>
            ) {
                val  result = response.body()
                if (result != null){
                    if (result.isSuccess()){
                        if (result.data.datas.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestIntegralHistorySuccess(result.data.pageCount,result.data.datas)
                        }
                    }else{
                        getView()?.showError(result.errorMsg)
                    }
                }else{
                    getView()?.showEmpty()
                }
            }
        })
    }
}