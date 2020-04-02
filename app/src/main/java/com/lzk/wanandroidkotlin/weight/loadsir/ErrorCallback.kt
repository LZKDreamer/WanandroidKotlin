package com.lzk.wanandroidkotlin.weight.loadsir

import com.kingja.loadsir.callback.Callback
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
class ErrorCallback : Callback() {
    override fun onCreateView(): Int = R.layout.error_callback
}