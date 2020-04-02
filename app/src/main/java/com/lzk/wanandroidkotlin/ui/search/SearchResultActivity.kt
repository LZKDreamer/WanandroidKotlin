package com.lzk.wanandroidkotlin.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.eventbus.LoginEvent
import com.lzk.wanandroidkotlin.eventbus.LogoutEvent
import com.lzk.wanandroidkotlin.ui.home.ArticleAdapter
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.ui.user.LoginActivity
import com.lzk.wanandroidkotlin.ui.user.UserHelper
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.greenrobot.eventbus.Subscribe

class SearchResultActivity : BaseMVPActivity<SearchResultContract.View,SearchResultPresenter>(),SearchResultContract.View {

    private lateinit var mKey: String
    private lateinit var mLoadSirService: LoadService<Any>
    private var mAdapter = ArticleAdapter()

    companion object{
        private const val INTENT_KEY = "INTENT_KEY"
        fun start(context: Context,key: String){
            context.startActivity(Intent(context,SearchResultActivity::class.java).apply {
                putExtra(INTENT_KEY,key)
            })
        }
    }

    override fun isRegisterEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mKey = intent.getStringExtra(INTENT_KEY)!!
        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestSearch(mPageNum,mKey)
        }

        showLoading()
        mPresenter.requestSearch(mPageNum,mKey)

        toolbar.apply {
            title = mKey
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount) setNoMoreData(true)
                    else{
                        mPresenter.requestSearch(mPageNum,mKey)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestSearch(mPageNum,mKey)
                }

            })
        }

        mAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                WebActivity.start(this@SearchResultActivity,data[position])
            }

            setOnItemClickListener { adapter, view, position ->
                if (UserHelper.isLogin()){
                    mAdapter.data[position].let {
                        if (it.collect){
                            mPresenter.requestUnCollect(position,it.id)
                        }else{
                            mPresenter.requestCollect(position,it.id)
                        }
                    }
                }else{
                    LoginActivity.start(this@SearchResultActivity)
                }
            }
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = mAdapter
        }


    }

    override fun createPresenter(): SearchResultPresenter = SearchResultPresenter()

    override fun getResId(): Int = R.layout.activity_search_result

    override fun requestSearchSuccess(
        pageCount: Int,
        data: MutableList<ArticleBean>
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

    override fun requestCollectResult(isSuccess: Boolean,msg: String) {
        showToast(if (isSuccess) getString(R.string.collect_success) else msg)
    }

    override fun requestUnCollectResult(isSuccess: Boolean,msg: String) {
        showToast(if (isSuccess) getString(R.string.uncollect_success) else msg)
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
                if (element.id == event.articleId){
                    element.collect = event.collect
                    notifyItemChanged(index)
                    break
                }
            }
        }
    }
}
