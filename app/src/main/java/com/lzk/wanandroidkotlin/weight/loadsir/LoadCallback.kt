package com.lzk.wanandroidkotlin.weight.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
class LoadCallback : Callback() {
    override fun onCreateView(): Int = R.layout.load_callback

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}