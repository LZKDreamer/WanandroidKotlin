package com.lzk.wanandroidkotlin.ui.main

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.base.BaseActivity
import com.lzk.wanandroidkotlin.ui.home.HomeFragment
import com.lzk.wanandroidkotlin.ui.me.MeFragment
import com.lzk.wanandroidkotlin.ui.tree.nav.NavigationFragment
import com.lzk.wanandroidkotlin.ui.project.ProjectFragment
import com.lzk.wanandroidkotlin.ui.tree.TreeFragment
import com.lzk.wanandroidkotlin.ui.wx.WxFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mFragmentList = mutableListOf<Fragment>()

    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragments()
        switchFragment(0)
        initNavigationView()
    }

    override fun getResId(): Int = R.layout.activity_main

    private fun initFragments(){
        mFragmentList.add(HomeFragment.newInstance())
        mFragmentList.add(ProjectFragment.newInstance())
        mFragmentList.add(TreeFragment.newInstance())
        mFragmentList.add(WxFragment.newInstance())
        mFragmentList.add(MeFragment.newInstance())
    }

    private fun initNavigationView(){
        mMainBottomNavView.apply {
            enableAnimation(false)
            setTextSize(12f)
            setIconSize(20f,20f)

            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.bottom_nav_home -> switchFragment(0)
                    R.id.bottom_nav_project -> switchFragment(1)
                    R.id.bottom_nav_navigation -> switchFragment(2)
                    R.id.bottom_nav_wx -> switchFragment(3)
                    R.id.bottom_nav_me -> switchFragment(4)
                }
                true
            }
        }
    }

    private fun switchFragment(index: Int){
        if (index < mFragmentList.size){
            val fm: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = fm.beginTransaction()
            for (fragment in mFragmentList){
                if (fragment.isAdded && fragment.isVisible) transaction.hide(fragment)
            }

            val fragment = mFragmentList[index]
            if (fragment.isAdded ){
                if (!fragment.isVisible){
                    transaction.show(fragment)
                    transaction.commit()
                }
            }else{
                transaction.add(R.id.mMainContainer,fragment)
                transaction.commit()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - mExitTime > 2000){
                showToast(getString(R.string.exit_app))
                mExitTime = System.currentTimeMillis()
            }else{
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
