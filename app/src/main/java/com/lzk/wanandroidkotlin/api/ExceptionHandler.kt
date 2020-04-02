package com.lzk.wanandroidkotlin.api

import android.net.ParseException
import android.text.TextUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.gyf.immersionbar.ktx.isSupportNavigationIconDark
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:异常信息处理
 */
object ExceptionHandler {

    fun getErrorMessage(t: Throwable): String{
        val msg = t.message
        return when(t){
            is UnknownHostException ->  "网络不可用，请检查网络后重试"
            is IllegalArgumentException -> "非法数据异常"
            is SocketTimeoutException -> "请求网络超时，请检查网络后重试"
            is HttpException -> convertStatusCode(t)
            is JsonParseException, is ParseException,is JSONException, is JsonIOException -> "数据解析错误"
            else -> msg?:"请求失败，请稍后再试"
        }
    }

    private fun convertStatusCode(httpException: HttpException): String {
        return when {
            httpException.code() == 500 -> {
                "服务器发生错误"
            }
            httpException.code() == 404 -> {
                "请求地址不存在"
            }
            httpException.code() == 403 -> {
                "请求被服务器拒绝"
            }
            httpException.code() == 307 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message()
            }
        }
    }
}