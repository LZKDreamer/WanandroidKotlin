package com.lzk.wanandroidkotlin.base

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
interface IBaseView {
    fun showLoading()
    //type：一个Presenter有多个接口都需要调用该方法时，提供给View层区分
    fun showError(msg: String, type: Int = -1)
    fun showEmpty()
}