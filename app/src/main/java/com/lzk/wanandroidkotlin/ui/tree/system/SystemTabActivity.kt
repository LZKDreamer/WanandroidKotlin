package com.lzk.wanandroidkotlin.ui.tree.system

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.SystemBean
import com.lzk.wanandroidkotlin.base.BaseActivity
import com.lzk.wanandroidkotlin.ui.main.IndicatorPagerAdapter
import kotlinx.android.synthetic.main.include_indicator_pager.*
import kotlinx.android.synthetic.main.include_toolbar.*
import net.lucode.hackware.magicindicator.ViewPagerHelper

class SystemTabActivity : BaseActivity() {

    private var mSystemBean: SystemBean? = null
    private var mIndex = 0

    companion object{
        private const val INTENT_SYSTEM_BEAN = "INTENT_SYSTEM_BEAN"
        private const val INTENT_SYSTEM_POSITION = "INTENT_SYSTEM_POSITION"

        fun start(context: Context,position: Int,data: SystemBean){
            context.startActivity(Intent(context,SystemTabActivity::class.java).apply {
                putExtra(INTENT_SYSTEM_BEAN,data)
                putExtra(INTENT_SYSTEM_POSITION,position)
            })
        }
    }

    override fun getResId(): Int = R.layout.activity_system_tab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            mSystemBean = it.getSerializableExtra(INTENT_SYSTEM_BEAN) as? SystemBean
            mIndex = it.getIntExtra(INTENT_SYSTEM_POSITION,mIndex)
        }
        mSystemBean?.let { it ->
            toolbar.apply {
                title = it.name
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    finish()
                }
            }
            val fragments = mutableListOf<Fragment>()
            it.children.forEach {bean ->
                fragments.add(SystemInfoFragment.newInstance(bean.id))
            }

            indicator_viewpager.adapter = IndicatorPagerAdapter(fragments,supportFragmentManager)
            indicator.navigator = getCommonNavigator(indicator_viewpager,it.children.map {i -> i.name })

            ViewPagerHelper.bind(indicator,indicator_viewpager)
            indicator_viewpager.setCurrentItem(mIndex,false)
        }
    }


}
