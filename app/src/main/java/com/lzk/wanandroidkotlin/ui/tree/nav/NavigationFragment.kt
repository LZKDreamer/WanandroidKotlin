package com.lzk.wanandroidkotlin.ui.tree.nav

import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService

import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.NavigationBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*

/**
 * A simple [Fragment] subclass.
 */
class NavigationFragment : BaseMVPFragment<NavigationContract.View,NavigationPresenter>(),NavigationContract.View {

    private lateinit var mLoadSirService: LoadService<Any>
    private var mAdapter: NavigationAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_list

    override fun initView(view: View) {
        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestNavigation()
        }

        mRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                mPresenter.requestNavigation()
            }
        }
    }

    override fun onLazyInit() {
        showLoading()
        mPresenter.requestNavigation()
    }

    override fun createPresenter(): NavigationPresenter = NavigationPresenter()

    override fun requestNavigationSuccess(data: MutableList<NavigationBean>) {
        LoadSirHelper.showSuccess(mLoadSirService)
        if (mAdapter == null){
            mAdapter = NavigationAdapter(data)
            mAdapter?.setOnTagClickListener(object : NavigationAdapter.OnTagClickListener{
                override fun onTagClick(position: Int, data: ArticleBean) {
                    WebActivity.start(mContext,data)
                }
            })

            mRecyclerview.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }else{
            mRefreshLayout.finishRefresh()
            mAdapter?.setNewData(data)
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

}
