package com.lzk.wanandroidkotlin.weight.loadsir

import com.kingja.loadsir.callback.Callback
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/25
 * Function:
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int = R.layout.empty_callback
}