package com.lzk.wanandroidkotlin.ui.project

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
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
import org.greenrobot.eventbus.Subscribe

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class ProjectChildFragment : BaseMVPFragment<ProjectChildContract.View,ProjectChildPresenter>(),ProjectChildContract.View {

    private lateinit var mLoadSirService: LoadService<Any>
    private var mProjectId = 0
    private var mAdapter = ArticleAdapter()

    init {
        mPageNum = 1
        mInitPage = 1//页数从1开始
    }

    companion object{
        private const val BUNDLE_ID = "BUNDLE_ID"
        fun newInstance(id: Int): ProjectChildFragment{
            return ProjectChildFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_ID,id)
                }
            }
        }
    }

    override fun createPresenter(): ProjectChildPresenter = ProjectChildPresenter()

    override fun getLayoutResId(): Int  = R.layout.fragment_list

    override fun isRegisterEventBus(): Boolean = true

    override fun initView(view: View) {
        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestProjectList(mPageNum,mProjectId)
        }

        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter.apply {
                setOnItemClickListener { adapter, view, position ->
                    WebActivity.start(mContext,data[position])
                }

                setOnItemChildClickListener{ adapter, view, position ->
                    if (UserHelper.isLogin()){
                        mAdapter.data[position].let {
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
        }

        mRefreshLayout.apply {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    if (mPageNum >= mPageCount){
                        setNoMoreData(true)
                    }else{
                        mPresenter.requestProjectList(mPageNum,mProjectId)
                    }
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mPageNum = mInitPage
                    mPresenter.requestProjectList(mPageNum,mProjectId)
                }

            })
        }
    }

    override fun onLazyInit() {
        mProjectId = arguments?.getInt(BUNDLE_ID) ?: 0
        showLoading()
        mPresenter.requestProjectList(mPageNum,mProjectId)
    }

    override fun requestProjectsSuccess(pageCount: Int,data: MutableList<ArticleBean>) {
        mPageCount = pageCount
        LoadSirHelper.showSuccess(mLoadSirService)
        if (isRefreshState()){
            mRefreshLayout.finishRefresh()
            mAdapter.setNewData(data)
        }else{
            mRefreshLayout.finishLoadMore()
            mAdapter.addData(data)
        }
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