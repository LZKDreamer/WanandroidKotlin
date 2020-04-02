package com.lzk.wanandroidkotlin.ui.tree.system

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.ui.home.ArticleAdapter
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/28
 * Function:
 */
class SystemInfoFragment : BaseMVPFragment<SystemInfoContract.View,SystemInfoPresenter>(),SystemInfoContract.View {

    private lateinit var mLoadSirService: LoadService<Any>
    private var mId: Int = 0
    private val mAdapter = ArticleAdapter()

    companion object{
        private const val BUNDLE_ID = "BUNDLE_ID"
        fun newInstance(id: Int): SystemInfoFragment{
            return SystemInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_ID,id)
                }
            }
        }
    }

    override fun createPresenter(): SystemInfoPresenter  = SystemInfoPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_list

    override fun initView(view: View) {
        arguments?.let {
            mId = it.getInt(BUNDLE_ID)
        }
        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestSystemInfo(mPageNum,mId)
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount) setNoMoreData(true)
                    else{
                        mPresenter.requestSystemInfo(mPageNum,mId)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestSystemInfo(mPageNum,mId)
                }

            })
        }
    }

    override fun onLazyInit() {
        showLoading()
        mPresenter.requestSystemInfo(mPageNum, mId)
    }

    override fun requestSystemInfoSuccess(pageCount: Int, data: MutableList<ArticleBean>) {
        mPageCount = pageCount
        if (isRefreshState()){
            LoadSirHelper.showSuccess(mLoadSirService)
            mRefreshLayout.finishRefresh()
            mAdapter.setNewData(data)
        }else{
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(data)
        }
        mPageNum++
    }

    override fun showLoading() {
        LoadSirHelper.showLoading(mLoadSirService)
    }

    override fun showError(msg: String, type: Int) {
        LoadSirHelper.showError(mLoadSirService,msg)
    }

    override fun showEmpty() {
        LoadSirHelper.showEmpty(mLoadSirService)
    }

}