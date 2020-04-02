package com.lzk.wanandroidkotlin.ui.collect

import android.util.Log
import android.widget.MultiAutoCompleteTextView
import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.ArticlesPageBean
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.CollectBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
class CollectPresenter : BasePresenter<CollectContract.View>(),CollectContract.Presenter {

    override fun requestCollectList(page: Int) {
        RetrofitUtil.getService().getCollections(page).enqueue(object : Callback<BaseBean<ArticlesPageBean<MutableList<CollectBean>>>>{
            override fun onFailure(
                call: Call<BaseBean<ArticlesPageBean<MutableList<CollectBean>>>>,
                t: Throwable
            ) {
                getView()?.showError(ExceptionHandler.getErrorMessage(t))
            }

            override fun onResponse(
                call: Call<BaseBean<ArticlesPageBean<MutableList<CollectBean>>>>,
                response: Response<BaseBean<ArticlesPageBean<MutableList<CollectBean>>>>
            ) {
                val result = response.body()
                if (result == null){
                    getView()?.showEmpty()
                }else{
                    if (result.isSuccess()){
                        if (result.data.datas.isNullOrEmpty()){
                            getView()?.showEmpty()
                        }else{
                            getView()?.requestCollectListSuccess(result.data.pageCount,result.data.datas)
                        }
                    }else{
                        getView()?.showError(result.errorMsg)
                    }
                }
            }

        })
    }

    override fun requestUnCollect(position: Int, articleId: Int) {
        RetrofitUtil.getService().getUnCollect(articleId).enqueue(object : Callback<BaseBean<ArticleBean>>{
            override fun onFailure(call: Call<BaseBean<ArticleBean>>, t: Throwable) {
                getView()?.requestUnCollectResult(false,ExceptionHandler.getErrorMessage(t),position)
            }

            override fun onResponse(
                call: Call<BaseBean<ArticleBean>>,
                response: Response<BaseBean<ArticleBean>>
            ) {
                val result = response.body()
                result?.let {
                    getView()?.requestUnCollectResult(it.isSuccess(),it.errorMsg,position)
                    if (it.isSuccess()){
                        EventBusUtil.sendEvent(CollectEvent(false,articleId))
                    }
                }
            }
        })
    }
}