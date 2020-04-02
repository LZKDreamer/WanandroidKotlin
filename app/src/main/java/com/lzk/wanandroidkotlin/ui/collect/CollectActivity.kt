package com.lzk.wanandroidkotlin.ui.collect

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.CollectBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.base.BasePresenter
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.ui.home.ArticleAdapter
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.greenrobot.eventbus.Subscribe

class CollectActivity : BaseMVPActivity<CollectContract.View,CollectPresenter>(),CollectContract.View {

    private lateinit var mLoadSirService: LoadService<Any>
    private var mAdapter: CollectAdapter = CollectAdapter()

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,CollectActivity::class.java))
        }
    }

    override fun isRegisterEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestCollectList(mPageNum)
        }

        toolbar.apply {
            title = getString(R.string.my_collection)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }

        mAdapter.apply {

            setOnItemClickListener { adapter, view, position ->
                WebActivity.start(this@CollectActivity,data[position])
            }
            setOnItemChildClickListener { adapter, view, position ->
                mPresenter.requestUnCollect(position,data[position].originId)
            }
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@CollectActivity)
            adapter = mAdapter
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount){
                        setNoMoreData(true)
                    }else{
                        mPresenter.requestCollectList(mPageNum)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestCollectList(mPageNum)
                }

            })
        }

        showLoading()
        mPresenter.requestCollectList(mPageNum)
    }

    override fun createPresenter(): CollectPresenter = CollectPresenter()

    override fun getResId(): Int = R.layout.activity_collect

    override fun requestCollectListSuccess(pageCount: Int,data: MutableList<CollectBean>) {
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

    override fun requestUnCollectResult(isSuccess: Boolean, msg: String,position: Int) {
        if (!isSuccess){
            showToast(msg)
        }
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

    @Subscribe
    fun onCollectEvent(event: CollectEvent){
        mAdapter.apply {
            for ((index,element) in data.withIndex()){
                if (element.originId == event.articleId){
                    if (data.size>1){
                        remove(index)
                    }else{
                        showEmpty()
                    }
                    break
                }
            }
        }
    }

}
