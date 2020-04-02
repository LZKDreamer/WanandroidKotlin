package com.lzk.wanandroidkotlin.api

import com.lzk.wanandroidkotlin.api.model.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
interface ApiService {
    companion object{
        const val BASE_URL = "https://www.wanandroid.com"
    }

    //Banner
    @GET("/banner/json")
    fun getBanner(): Call<BaseBean<MutableList<BannerBean>>>

    //首页文章
    @GET("/article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //置顶文章
    @GET("/article/top/json")
    fun getHomeTopArticles(): Call<BaseBean<MutableList<ArticleBean>>>

    //项目分类
    @GET("/project/tree/json")
    fun getProjectCategory(): Call<BaseBean<MutableList<CategoryBean>>>

    //项目列表
    @GET("/project/list/{page}/json")
    fun getProjectList(@Path("page") pageNum: Int,@Query("cid") id: Int): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //广场列表
    @GET("/user_article/list/{page}/json")
    fun getSquareList(@Path("page") pageNum: Int): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //导航
    @GET("/navi/json")
    fun getNavigation(): Call<BaseBean<MutableList<NavigationBean>>>

    //体系
    @GET("/tree/json")
    fun getSystem(): Call<BaseBean<MutableList<SystemBean>>>

    //知识体系下的文章
    @GET("/article/list/{page}/json")
    fun getSystemInfo(@Path("page") page: Int,@Query("cid") id: Int): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //公众号Tab
    @GET("/wxarticle/chapters/json")
    fun getWxTab(): Call<BaseBean<MutableList<CategoryBean>>>

    //公众号列表
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getWxArticles(@Path("id") id: Int,@Path("page") page: Int): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //个人积分
    @GET("/lg/coin/userinfo/json")
    fun getIntegral(): Call<BaseBean<IntegralBean>>

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun getLogin(@Field("username") username: String,@Field("password") password: String): Call<BaseBean<UserBean>>

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    fun getRegister(@Field("username") username: String,
                    @Field("password") password: String,
                    @Field("repassword") repassword: String): Call<BaseBean<UserBean>>

    //退出登录
    @GET("/user/logout/json")
    fun getLogout(): Call<BaseBean<UserBean>>

    //搜索热词
    @GET("/hotkey/json")
    fun getHotKey(): Call<BaseBean<MutableList<HotKeyBean>>>

    //搜索
    @POST("/article/query/{page}/json")
    fun getSearch(@Path("page") pageNum: Int,@Query("k") key: String): Call<BaseBean<ArticlesPageBean<MutableList<ArticleBean>>>>

    //收藏
    @POST("/lg/collect/{id}/json")
    fun getCollect(@Path("id") id: Int): Call<BaseBean<ArticleBean>>

    //取消收藏
    @POST("/lg/uncollect_originId/{id}/json")
    fun getUnCollect(@Path("id") id: Int): Call<BaseBean<ArticleBean>>

    //我的积分历史记录
    @GET("/lg/coin/list/{page}/json")
    fun getIntegralHistory(@Path("page") pageNum: Int): Call<BaseBean<ArticlesPageBean<MutableList<IntegralHistoryBean>>>>

    //积分排行榜
    @GET("/coin/rank/{page}/json")
    fun getIntegralRankList(@Path("page") pageNum: Int): Call<BaseBean<ArticlesPageBean<MutableList<IntegralBean>>>>

    //我的收藏
    @GET("/lg/collect/list/{page}/json")
    fun getCollections(@Path("page") pageNum: Int): Call<BaseBean<ArticlesPageBean<MutableList<CollectBean>>>>
}