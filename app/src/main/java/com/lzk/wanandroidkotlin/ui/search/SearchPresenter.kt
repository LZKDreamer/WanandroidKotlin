package com.lzk.wanandroidkotlin.ui.search

import com.lzk.wanandroidkotlin.api.ExceptionHandler
import com.lzk.wanandroidkotlin.api.RetrofitUtil
import com.lzk.wanandroidkotlin.api.model.BaseBean
import com.lzk.wanandroidkotlin.api.model.HotKeyBean
import com.lzk.wanandroidkotlin.base.BasePresenter
import com.lzk.wanandroidkotlin.utils.DBHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
class SearchPresenter : BasePresenter<SearchContract.View>(),SearchContract.Presenter {
    override fun requestHotKey() {
        RetrofitUtil.getService().getHotKey().enqueue(object : Callback<BaseBean<MutableList<HotKeyBean>>>{
            override fun onFailure(call: Call<BaseBean<MutableList<HotKeyBean>>>, t: Throwable) {
                //失败就不管了
            }

            override fun onResponse(
                call: Call<BaseBean<MutableList<HotKeyBean>>>,
                response: Response<BaseBean<MutableList<HotKeyBean>>>
            ) {
                val result = response.body()
                if (result!!.isSuccess()){
                    getView()?.requestHotKeySuccess(result.data)
                }
                //失败不管了
            }

        })
    }

    override fun requestSearchHistory() {
        getView()?.requestSearchHistorySuccess(DBHelper.queryAllHistories())
    }
}