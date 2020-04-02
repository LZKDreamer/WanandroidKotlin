package com.lzk.wanandroidkotlin.base

import android.os.Bundle
import android.view.View

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
abstract class BaseMVPFragment<V: IBaseView,P: BasePresenter<V>> : BaseFragment(){
    protected lateinit var mPresenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    abstract fun createPresenter(): P
}