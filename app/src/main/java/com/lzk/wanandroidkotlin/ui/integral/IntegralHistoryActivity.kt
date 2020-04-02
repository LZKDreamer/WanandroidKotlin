package com.lzk.wanandroidkotlin.ui.integral

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.LoadSirUtil
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.IntegralHistoryBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

class IntegralHistoryActivity : BaseMVPActivity<IntegralHistoryContract.View,IntegralHistoryPresenter>(),IntegralHistoryContract.View {

    init {
        mPageNum = 1
        mInitPage = 1
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,IntegralHistoryActivity::class.java))
        }
    }

    private lateinit var mLoadSirService: LoadService<Any>
    private var mAdapter: IntegralHistoryAdapter = IntegralHistoryAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.apply {
            title = getString(R.string.my_integral)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }

        mLoadSirService  = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestIntegralHistory(mPageNum)
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@IntegralHistoryActivity)
            adapter = mAdapter
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount){
                        setNoMoreData(true)
                    }else{
                        mPresenter.requestIntegralHistory(mPageNum)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestIntegralHistory(mPageNum)
                }
            })
        }

        showLoading()
        mPresenter.requestIntegralHistory(mPageNum)
    }

    override fun createPresenter(): IntegralHistoryPresenter = IntegralHistoryPresenter()

    override fun getResId(): Int = R.layout.activity_integral_history

    override fun requestIntegralHistorySuccess(
        pageCount: Int,
        data: MutableList<IntegralHistoryBean>
    ) {
        LoadSirHelper.showSuccess(mLoadSirService)
        if (isRefreshState()){
            mRefreshLayout.finishRefresh()
            mAdapter.setNewData(data)
        }else{
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(data)
        }
        mPageCount = pageCount
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
