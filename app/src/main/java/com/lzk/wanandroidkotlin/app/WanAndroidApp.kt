package com.lzk.wanandroidkotlin.app

import android.app.Application
import android.content.Context
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import org.litepal.LitePal

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
class WanAndroidApp : Application() {


    companion object{
        private lateinit var mContext: Context
        private lateinit var mInstance: WanAndroidApp

        fun getInstance(): WanAndroidApp{
            return mInstance
        }

        fun getContext(): Context{
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        LoadSirHelper.build()
        mContext = applicationContext
        mInstance = this
        LitePal.initialize(this)
    }
}