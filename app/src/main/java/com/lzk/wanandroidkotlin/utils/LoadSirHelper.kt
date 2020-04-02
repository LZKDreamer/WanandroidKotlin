package com.lzk.wanandroidkotlin.utils

import android.view.View
import android.widget.TextView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.weight.loadsir.EmptyCallback
import com.lzk.wanandroidkotlin.weight.loadsir.ErrorCallback
import com.lzk.wanandroidkotlin.weight.loadsir.LoadCallback
import com.lzk.wanandroidkotlin.weight.loadsir.NetworkCallback

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
object LoadSirHelper {

    //全局配置
    fun build(){
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadCallback())
            .addCallback(NetworkCallback())
            .setDefaultCallback(LoadCallback::class.java) //设置默认状态页
            .commit()
    }

    //注册
    fun register(view: View,listener: (view: View) -> Unit): LoadService<Any> = LoadSir.getDefault().register(view,listener)

    //加载
    fun showLoading(loadSirService: LoadService<Any>){
        loadSirService.showCallback(LoadCallback::class.java)
    }

    //空
    fun showEmpty(loadSirService: LoadService<Any>){
        loadSirService.showCallback(EmptyCallback::class.java)
    }

    //错误
    fun showError(loadSirService: LoadService<Any>,msg: String){
        loadSirService.setCallBack(ErrorCallback::class.java){_, view ->
            view.findViewById<TextView>(R.id.error_text_tv).apply {
                text = msg
            }
        }
        loadSirService.showCallback(ErrorCallback::class.java)
    }

    //成功
    fun showSuccess(loadSirService: LoadService<Any>){
        loadSirService.showSuccess()
    }
}

