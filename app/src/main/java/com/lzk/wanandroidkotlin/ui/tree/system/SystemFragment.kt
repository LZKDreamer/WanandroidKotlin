package com.lzk.wanandroidkotlin.ui.tree.system

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.SystemBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import kotlinx.android.synthetic.main.include_refresh_recyclerview.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class SystemFragment : BaseMVPFragment<SystemContract.View,SystemPresenter>(),SystemContract.View{

    private lateinit var mLoadSirService: LoadService<Any>

    private var mAdapter: SystemAdapter? = null

    override fun createPresenter(): SystemPresenter = SystemPresenter()

    override fun getLayoutResId(): Int = R.layout.fragment_list

    override fun initView(view: View) {
        mLoadSirService = LoadSirHelper.register(mRefreshLayout){
            showLoading()
            mPresenter.requestSystem()
        }

        mRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                mPresenter.requestSystem()
            }
        }
    }

    override fun onLazyInit() {
        showLoading()
        mPresenter.requestSystem()
    }

    override fun requestSystemSuccess(data: MutableList<SystemBean>) {
        LoadSirHelper.showSuccess(mLoadSirService)
        if (mAdapter == null){
            mAdapter = SystemAdapter(data)
            mAdapter?.run{
                setOnTagClickListener(object : SystemAdapter.OnTagClickListener{
                    override fun onTagClick(position: Int, data: SystemBean) {
                        SystemTabActivity.start(mContext,position,data)
                    }
                })
                setOnItemClickListener { adapter, view, position ->
                    SystemTabActivity.start(mContext,0,data[position])
                }
            }
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