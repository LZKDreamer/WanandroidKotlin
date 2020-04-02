package com.lzk.wanandroidkotlin.base

import android.os.Bundle

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
abstract class BaseMVPActivity<V: IBaseView,P: BasePresenter<V>> : BaseActivity(){
    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    abstract fun createPresenter(): P
}
