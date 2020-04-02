package com.lzk.wanandroidkotlin.ui.tree

import android.view.View
import androidx.fragment.app.Fragment
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.base.BaseFragment
import com.lzk.wanandroidkotlin.ui.main.IndicatorPagerAdapter
import com.lzk.wanandroidkotlin.ui.tree.nav.NavigationFragment
import com.lzk.wanandroidkotlin.ui.tree.square.SquareFragment
import com.lzk.wanandroidkotlin.ui.tree.system.SystemFragment
import kotlinx.android.synthetic.main.include_indicator_pager.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class TreeFragment : BaseFragment() {

    companion object{
        fun newInstance() : TreeFragment = TreeFragment()
    }

    override fun getLayoutResId(): Int = R.layout.layout_indicator_pager

    override fun initView(view: View) {
        val fragments = arrayListOf<Fragment>(SquareFragment(),NavigationFragment(),SystemFragment())
        indicator_viewpager. adapter = IndicatorPagerAdapter(fragments,childFragmentManager)
        indicator.apply {
            navigator = getCommonNavigator(indicator_viewpager,
                arrayListOf(
                    getString(R.string.square),
                    getString(R.string.navigation),
                    getString(R.string.system)
                ))
        }
        ViewPagerHelper.bind(indicator,indicator_viewpager)
        indicator_viewpager.setCurrentItem(1,false)
    }

    override fun onLazyInit() {

    }
}