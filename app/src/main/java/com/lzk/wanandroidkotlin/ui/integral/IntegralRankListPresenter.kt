package com.lzk.wanandroidkotlin.ui.integral

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
class IntegralRankListPresenter : BasePresenter<IntegralRankListContract.View>(),IntegralRankListContract.Presenter {

    override fun requestIntegralRankList(pageNum: Int) {
        RetrofitUtil.getService().getIntegralRankList(pageNum).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<IntegralBean>>>>{
            override fun onFailure(
                call: Call<BaseBean<ArticlesPageBean<MutableList<IntegralBean>>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<ArticlesPageBean<MutableList<IntegralBean>>>>,
                response: Response<BaseBean<ArticlesPageBean<MutableList<IntegralBean>>>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        if (result.data.datas.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestIntegralRankListSuccess(result.data.pageCount,result.data.datas)
                        }
                    }else{
                        getView()?.showError(result.errorMsg)
                    }
                }
            }

        })
    }
}