package com.lzk.wanandroidkotlin.ui.integral

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

class IntegralRankListActivity : BaseMVPActivity<IntegralRankListContract.View,IntegralRankListPresenter>(),IntegralRankListContract.View {

    private lateinit var mLoadSirService: LoadService<Any>
    private val mAdapter = IntegralRankListAdapter(mutableListOf())

    init {
        mPageNum = 1
        mInitPage = 1
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,IntegralRankListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.apply {
            title = getString(R.string.ranking_list)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }

        mLoadSirService  = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestIntegralRankList(mPageNum)
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@IntegralRankListActivity)
            adapter = mAdapter
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount){
                        setNoMoreData(true)
                    }else{
                        mPresenter.requestIntegralRankList(mPageNum)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestIntegralRankList(mPageNum)
                }
            })
        }

        showLoading()
        mPresenter.requestIntegralRankList(mPageNum)
    }

    override fun createPresenter(): IntegralRankListPresenter = IntegralRankListPresenter()

    override fun getResId(): Int = R.layout.activity_integral_rank_list

    override fun requestIntegralRankListSuccess(pageCount: Int, data: MutableList<IntegralBean>) {
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
