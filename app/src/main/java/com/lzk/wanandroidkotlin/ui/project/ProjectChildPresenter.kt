package com.lzk.wanandroidkotlin.ui.project

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class ProjectChildPresenter() : BasePresenter<ProjectChildContract.View>(),ProjectChildContract.Presenter{

    override fun requestProjectList(page: Int,id: Int) {
        RetrofitUtil.getService().getProjectList(page,id).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>{
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
                            getView()?.requestProjectsSuccess(result.data.pageCount,result.data.datas)
                        }
                    }else{
                        getView()?.showError(result.errorMsg, -1)
                    }
                }
            }

        })
    }

    override fun requestCollect(position: Int, articleId: Int) {
        RetrofitUtil.getService().getCollect(articleId).enqueue(object : Callback<BaseBean<ArticleBean>>{
            override fun onFailure(call: Call<BaseBean<ArticleBean>>, t: Throwable) {
                getView()?.requestCollectResult(false,ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<ArticleBean>>,
                response: Response<BaseBean<ArticleBean>>
            ) {
                val result = response.body()
                result?.let {
                    getView()?.requestCollectResult(it.isSuccess(),it.errorMsg)
                    if (it.isSuccess()){
                        EventBusUtil.sendEvent(CollectEvent(true,articleId))
                    }
                }
            }

        })
    }

    override fun requestUnCollect(position: Int, articleId: Int) {
        RetrofitUtil.getService().getUnCollect(articleId).enqueue(object : Callback<BaseBean<ArticleBean>>{
            override fun onFailure(call: Call<BaseBean<ArticleBean>>, t: Throwable) {
                getView()?.requestUnCollectResult(false,ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<ArticleBean>>,
                response: Response<BaseBean<ArticleBean>>
            ) {
                val result = response.body()
                result?.let {
                    getView()?.requestUnCollectResult(it.isSuccess(),it.errorMsg)
                    if (it.isSuccess()){
                        EventBusUtil.sendEvent(CollectEvent(false,articleId))
                    }
                }
            }
        })
    }
}