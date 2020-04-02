package com.lzk.wanandroidkotlin.base

import java.lang.ref.WeakReference

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
abstract class BasePresenter<V: IBaseView> {
    protected lateinit var reference: WeakReference<V>

    fun attachView(view: V){
        reference = WeakReference(view)
    }

    fun detachView(){
        reference.clear()
    }

    fun getView(): V? = reference.get()
}