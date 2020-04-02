package com.lzk.wanandroidkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import com.lzk.wanandroidkotlin.utils.htmlFormat
import com.lzk.wanandroidkotlin.weight.ScaleTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
abstract class BaseFragment : BaseLazyFragment() {

    protected var mPageNum = 0//当前页数
    protected var mPageCount = -1;//总页数
    protected var mInitPage = 0;//页数从几开始

    protected fun isRefreshState() = mPageNum <= mInitPage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRegisterEventBus()) EventBusUtil.register(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRegisterEventBus()) EventBusUtil.unregister(this)
    }

    //是否注册EventBus
    open fun isRegisterEventBus() = false

    fun showToast(msg: String){
        Toast.makeText(mContext,msg, Toast.LENGTH_SHORT).show()
    }

    fun getCommonNavigator(viewPager: ViewPager,titles: List<String>): CommonNavigator{
        return CommonNavigator(mContext).apply {
            adapter = object : CommonNavigatorAdapter() {
                override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                    return ScaleTransitionPagerTitleView(context).apply {
                        normalColor = ContextCompat.getColor(mContext, R.color.default_light)
                        selectedColor = ContextCompat.getColor(mContext, R.color.default_primary)
                        text = titles[index].htmlFormat()
                        textSize = 15f
                        setOnClickListener {
                            viewPager.setCurrentItem(index,false)
                        }
                    }
                }

                override fun getCount(): Int = titles.size

                override fun getIndicator(context: Context?): IPagerIndicator {
                    return LinePagerIndicator(context).apply {
                        mode = LinePagerIndicator.MODE_EXACTLY
                        lineHeight = UIUtil.dip2px(mContext, 3.0).toFloat()
                        lineWidth = UIUtil.dip2px(mContext, 15.0).toFloat()
                        roundRadius = UIUtil.dip2px(mContext, 3.0).toFloat()
                        startInterpolator = AccelerateInterpolator()
                        endInterpolator = DecelerateInterpolator(2f)
                        setColors(ContextCompat.getColor(mContext, R.color.default_primary))
                    }
                }
            }

        }
    }
}