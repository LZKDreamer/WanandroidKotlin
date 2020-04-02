package com.lzk.wanandroidkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.lzk.wanandroidkotlin.app.WanAndroidApp
import java.io.Serializable

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
class SPUtil {

    companion object{
        private const val SP_NAME = "wanandroid.pre"
        private val mSharedPreferences: SharedPreferences = WanAndroidApp.getContext().getSharedPreferences(
            SP_NAME,Context.MODE_PRIVATE)
        private val mEditor: SharedPreferences.Editor = mSharedPreferences.edit()

        val mInstance: SPUtil by lazy {
            SPUtil()
        }

        private val mGson by lazy {
            Gson()
        }
    }

    fun putString(key: String,value: String){
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getString(key: String,defaultValue: String) = mSharedPreferences.getString(key,defaultValue)

    fun putObject(key: String,bean: Any){
        mEditor.putString(key, mGson.toJson(bean))
        mEditor.commit()
    }

    fun <T: Serializable>getObject(key: String,clazz: Class<T>): T?{
        val result = mSharedPreferences.getString(key,"")
        if (result.isNullOrEmpty()) {
            return null
        }
        return mGson.fromJson(result,clazz)
    }

    fun remove(key: String){
        mEditor.remove(key)
        mEditor.commit()
    }
}