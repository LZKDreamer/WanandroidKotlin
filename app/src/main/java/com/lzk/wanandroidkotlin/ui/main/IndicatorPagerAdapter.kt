package com.lzk.wanandroidkotlin.ui.main

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class IndicatorPagerAdapter(private val fragments: List<Fragment>,fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }
}