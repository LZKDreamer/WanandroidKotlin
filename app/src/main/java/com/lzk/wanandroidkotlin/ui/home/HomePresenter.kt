package com.lzk.wanandroidkotlin.ui.home

import android.util.Log
import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BannerBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
class HomePresenter() : BasePresenter<HomeContract.View>(), HomeContract.Presenter{

    override fun requestBanner() {
        RetrofitUtil.getService().getBanner().enqueue(object : Callback<BaseBean<MutableList<BannerBean>>>{
            override fun onFailure(call: Call<BaseBean<MutableList<BannerBean>>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<BannerBean>>>,
                response: Response<BaseBean<MutableList<BannerBean>>>
            ) {
                if (response.body()!!.isSuccess()){
                    getView()?.requestBannerSuccess(response.body()!!.data)
                }else{
                    getView()?.showError(response.body()!!.errorMsg, -1)
                }
            }
        })
    }

    override fun requestHomeTopArticle() {
        RetrofitUtil.getService().getHomeTopArticles().enqueue(object : Callback<BaseBean<MutableList<ArticleBean>>>{
            override fun onFailure(call: Call<BaseBean<MutableList<ArticleBean>>>, t: Throwable) {
                getView()?.requestHomeTopArticleSuccess(null)
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<ArticleBean>>>,
                response: Response<BaseBean<MutableList<ArticleBean>>>
            ) {
                //不管是否成功都当作成功
                getView()?.requestHomeTopArticleSuccess(response.body()?.data)
            }

        })
    }

    override fun requestHomeArticle(pageNum: Int) {
        RetrofitUtil.getService().getHomeArticles(pageNum).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>{
            override fun onFailure(call: Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>, t: Throwable) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t), -1)
            }

            override fun onResponse(
                call: Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>,
                response: Response<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>
            ) {
                if (response.body()!!.isSuccess()){
                    if (response.body()!!.data.datas.isNullOrEmpty()){
                        getView()?.showEmpty()
                    }else{
                        Log.d("shiZi","curPage:"+response.body()!!.data.curPage)
                        getView()?.requestHomeArticleSuccess(response.body()!!.data.pageCount,response.body()!!.data.datas)
                    }
                }else{
                    getView()?.showError(response.body()!!.errorMsg, -1)
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