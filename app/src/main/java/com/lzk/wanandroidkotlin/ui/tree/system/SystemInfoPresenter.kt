package com.lzk.wanandroidkotlin.ui.tree.system

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/28
 * Function:
 */
class SystemInfoPresenter: BasePresenter<SystemInfoContract.View>(),SystemInfoContract.Presenter {
    override fun requestSystemInfo(pageNum: Int, id: Int) {
        RetrofitUtil.getService().getSystemInfo(pageNum,id).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>{
            override fun onFailure(
                call: Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>,
                response: Response<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        if (result.data.datas.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestSystemInfoSuccess(result.data.pageCount,result.data.datas)
                        }
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }
}