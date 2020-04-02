package com.lzk.wanandroidkotlin.ui.project

import androidx.fragment.app.Fragment
import android.view.View
import com.kingja.loadsir.core.LoadService

import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.CategoryBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.ui.main.IndicatorPagerAdapter
import com.lzk.wanandroidkotlin.utils.LoadSirHelper
import kotlinx.android.synthetic.main.include_indicator_pager.*
import net.lucode.hackware.magicindicator.ViewPagerHelper

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : BaseMVPFragment<ProjectContract.View,ProjectPresenter>(),ProjectContract.View {

    private lateinit var mLoadSirService: LoadService<Any>

    companion object{
        fun newInstance() : ProjectFragment = ProjectFragment()
    }

    override fun getLayoutResId(): Int = R.layout.layout_indicator_pager

    override fun initView(view: View) {
        mLoadSirService = LoadSirHelper.register(indicator_viewpager){
            showLoading()
            mPresenter.requestProjectCategory()
        }
    }

    override fun onLazyInit() {
        showLoading()
        mPresenter.requestProjectCategory()
    }

    override fun createPresenter(): ProjectPresenter = ProjectPresenter()

    override fun requestProjectCategorySuccess(data: MutableList<CategoryBean>) {
        initIndicatorAndPager(data)
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

    private fun initIndicatorAndPager(data: MutableList<CategoryBean>){
        LoadSirHelper.showSuccess(mLoadSirService)
        val fragments = mutableListOf<Fragment>()
        data.forEach {
            fragments.add(ProjectChildFragment.newInstance(it.id))
        }
        indicator_viewpager.adapter = IndicatorPagerAdapter(fragments,childFragmentManager)

        indicator.navigator = getCommonNavigator(indicator_viewpager,data.map { it.name })

        ViewPagerHelper.bind(indicator,indicator_viewpager)
    }

}
