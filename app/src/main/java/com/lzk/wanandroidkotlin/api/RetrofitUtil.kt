package com.lzk.wanandroidkotlin.api

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lzk.wanandroidkotlin.app.WanAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
object RetrofitUtil{

    private var mApiService: ApiService? = null

    private fun initCookieJar(): OkHttpClient{
        val cookieJar: ClearableCookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(WanAndroidApp.getContext()))
       return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
    }

    private fun initRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(initCookieJar())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ApiService{
        if (mApiService == null){
            mApiService = initRetrofit().create(ApiService::class.java)
        }
        return mApiService!!
    }
}