package com.lzk.wanandroidkotlin.ui.home

import android.os.UserHandle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.BannerBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.eventbus.CollectEvent
import com.lzk.wanandroidkotlin.eventbus.LoginEvent
import com.lzk.wanandroidkotlin.eventbus.LogoutEvent
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.ui.search.SearchActivity
import com.lzk.wanandroidkotlin.ui.user.LoginActivity
import com.lzk.wanandroidkotlin.ui.user.UserHelper
import com.lzk.wanandroidkotlin.utils.GlideUtil
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import com.lzk.wanandroidkotlin.utils.htmlFormat
import com.lzk.wanandroidkotlin.weight.CollectView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.include_banner.view.*
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.greenrobot.eventbus.Subscribe

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
class HomeFragment private constructor(): BaseMVPFragment<HomeContract.View,HomePresenter>(),
    HomeContract.View{

    private lateinit var loadSir: LoadService<Any>
    private var mAdapter: ArticleAdapter = ArticleAdapter(true)
    private var mArticleTopList: MutableList<ArticleBean> = mutableListOf()
    private var mBannerList: MutableList<BannerBean> = mutableListOf()

    companion object{
        fun newInstance() : HomeFragment = HomeFragment()
    }

    override fun createPresenter(): HomePresenter = HomePresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun isRegisterEventBus(): Boolean = true

    override fun initView(view: View) {
        initToolbar()

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }

        mAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                WebActivity.start(mContext,data[position])
            }
            setOnItemChildClickListener { adapter, view, position ->
                if (UserHelper.isLogin()){
                    this.data[position].let {
                        if (it.collect){
                            mPresenter.requestUnCollect(position,it.id)
                        }else{
                            mPresenter.requestCollect(position,it.id)
                        }
                    }
                }else{
                    LoginActivity.start(mContext)
                }
            }
        }

        loadSir = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestBanner()
        }

    }

    private fun initToolbar(){
        setHasOptionsMenu(true)
        toolbar.apply {
            title = getString(R.string.home)
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.home_menu_search ->{
                        SearchActivity.start(mContext)
                    }
                }
                true
            }
        }
    }

    override fun onLazyInit() {
        showLoading()
        mPresenter.requestBanner()
        //上拉加载下拉刷新
        mRefreshLayout.apply {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    //setRefreshLoadMoreState(STATE_LOADMORE)
                    if (mPageNum >= mPageCount) {
                        mRefreshLayout.setNoMoreData(true)
                    } else {
                        mPresenter.requestHomeArticle(mPageNum)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    //setRefreshLoadMoreState(STATE_REFRESH)
                    mPageNum = mInitPage
                    mBannerList.clear()
                    mArticleTopList.clear()
                    mPresenter.requestBanner()
                }

            })
            setEnableAutoLoadMore(true)
        }
    }

    override fun requestBannerSuccess(data: MutableList<BannerBean>?) {
        if (!data.isNullOrEmpty()) mBannerList.addAll(data)
        mPresenter.requestHomeTopArticle()
    }

    override fun requestHomeTopArticleSuccess(data: MutableList<ArticleBean>?) {
        if (!data.isNullOrEmpty()) mArticleTopList.addAll(data)
        mPresenter.requestHomeArticle(mPageNum)
    }

    override fun requestHomeArticleSuccess(pageCount: Int,data: MutableList<ArticleBean>?) {
        mPageCount = pageCount
        setRecyclerView(data)
        LoadSirHelper.showSuccess(loadSir)
        mPageNum++
    }

    override fun requestCollectResult(isSuccess: Boolean,msg: String) {
        showToast(if (isSuccess) getString(R.string.collect_success) else msg)
    }

    override fun requestUnCollectResult(isSuccess: Boolean,msg: String) {
        showToast(if (isSuccess) getString(R.string.uncollect_success) else msg)
    }

    override fun showLoading() {
        LoadSirHelper.showLoading(loadSir)
    }

    override fun showError(msg: String, type: Int) {
        if (isRefreshState()){
            LoadSirHelper.showError(loadSir,msg)
        }else{
            mRefreshLayout.finishLoadMore(false)
        }
    }

    override fun showEmpty() {
        if (isRefreshState()){
            LoadSirHelper.showEmpty(loadSir)
        }else{
            mRefreshLayout.finishLoadMore(false)
        }
    }

    private fun initBanner(data: MutableList<BannerBean>): View{
        return LayoutInflater.from(mContext).inflate(R.layout.include_banner,null).apply {
            banner.setAdapter(BGABanner.Adapter<ImageView,BannerBean> { banner, itemView, model, position ->
                GlideUtil.loadImage(mContext,model?.imagePath,itemView)
            })
            banner.setDelegate(BGABanner.Delegate<ImageView,BannerBean> { banner, itemView, model, position ->
                WebActivity.start(mContext,data[position])
            })
            banner.setData(data, data.map { it.title.htmlFormat() })
        }
    }

    private fun setRecyclerView(data: MutableList<ArticleBean>?){
        if (isRefreshState()){
            mRefreshLayout.finishRefresh()
        }else{
            if (data.isNullOrEmpty()){
                mRefreshLayout.finishLoadMoreWithNoMoreData()
            }else{
                mRefreshLayout.finishLoadMore()
            }
        }
        mAdapter.apply {
            if (isRefreshState()){
                //设置Banner
                setHeaderView(initBanner(mBannerList))
                if (!data.isNullOrEmpty()) mArticleTopList.addAll(data)
                setNewData(mArticleTopList)
            }else{
                if (!data.isNullOrEmpty()) addData(data)
            }
        }
    }

    @Subscribe
    fun onCollectEvent(event: CollectEvent){
        mAdapter.apply {
            for ((index,element) in data.withIndex()){
                if (element.id == event.articleId){
                    data[index].collect = event.collect
                    notifyItemChanged(index+headerLayoutCount)
                    break
                }
            }
        }
    }

    @Subscribe
    fun onLoginEvent(event: LoginEvent){
        event.data.collectIds.forEach {
            for (item in mAdapter.data){
                if (it == item.id){
                    item.collect = true
                    break
                }
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    @Subscribe
    fun onLogoutEvent(event: LogoutEvent){
        mAdapter.apply {
            for (item in mAdapter.data){
                item.collect = false
            }
            notifyDataSetChanged()
        }
    }
}