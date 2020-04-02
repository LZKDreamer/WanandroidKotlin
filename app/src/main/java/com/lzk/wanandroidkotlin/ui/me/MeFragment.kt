package com.lzk.wanandroidkotlin.ui.me

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import android.view.View

import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.BannerBean
import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.BaseMVPFragment
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import com.lzk.wanandroidkotlin.eventbus.LoginEvent
import com.lzk.wanandroidkotlin.eventbus.LogoutEvent
import com.lzk.wanandroidkotlin.ui.collect.CollectActivity
import com.lzk.wanandroidkotlin.ui.integral.IntegralHistoryActivity
import com.lzk.wanandroidkotlin.ui.integral.IntegralRankListActivity
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.ui.user.LoginActivity
import com.lzk.wanandroidkotlin.utils.GlideUtil
import com.lzk.wanandroidkotlin.ui.user.UserHelper
import com.lzk.wanandroidkotlin.weight.LoadingDialog
import kotlinx.android.synthetic.main.fragment_me.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class MeFragment : BaseMVPFragment<MeContract.View,MePresenter>(),MeContract.View,View.OnClickListener {

    companion object{
        fun newInstance() : MeFragment = MeFragment()

        private const val dog_img_url = "http://b-ssl.duitang.com/uploads/item/201810/22/20181022212449_pjhqc.jpeg"
    }

    override fun getLayoutResId(): Int = R.layout.fragment_me

    override fun initView(view: View) {
        if (UserHelper.isLogin()){
            UserHelper.getUserInfo()?.let {
                setLoginState(it)
            }

            UserHelper.getIntegral()?.let {
                me_login_level_tv.text = it.level.toString()
                me_login_rank_tv.text = it.rank.toString()
                me_login_integral_tv.text = it.coinCount.toString()
                me_my_integral_num_tv.text = it.coinCount.toString()
            }
        }else{
            me_logout_btn.visibility = View.GONE
        }

        me_card_view.setOnClickListener(this)
        me_logout_btn.setOnClickListener(this)
        me_my_integral_rl.setOnClickListener(this)
        me_rank_list_rl.setOnClickListener(this)
        me_my_collection_rl.setOnClickListener(this)
        me_project_address_rl.setOnClickListener(this)
        me_open_source_website_rl.setOnClickListener(this)
        me_open_api_rl.setOnClickListener(this)
    }

    override fun onLazyInit() {
        me_refresh_layout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                if (UserHelper.isLogin()){
                    mPresenter.requestIntegral()
                }else{
                    finishRefresh()
                }
            }
            if (UserHelper.isLogin()) autoRefresh()
        }
    }

    override fun createPresenter(): MePresenter = MePresenter()

    override fun isRegisterEventBus(): Boolean = true

    override fun requestIntegralSuccess(data: IntegralBean) {
        UserHelper.saveIntegral(data)
        me_refresh_layout.finishRefresh()
        me_login_level_tv.text = data.level.toString()
        me_login_rank_tv.text = data.rank.toString()
        me_login_integral_tv.text = data.coinCount.toString()
        me_my_integral_num_tv.text = data.coinCount.toString()
    }

    override fun requestLogoutSuccess() {
        LoadingDialog.dismiss()
        UserHelper.clearUserInfo()
        setLogoutState()
        EventBusUtil.sendEvent(LogoutEvent())
    }

    override fun showLoading() {

    }

    override fun showError(msg: String, type: Int) {
        if (type == MePresenter.TYPE_LOGOUT){
            LoadingDialog.dismiss()
        }else{//获取积分
            me_refresh_layout.finishRefresh()
        }
        showToast(msg)
    }

    override fun showEmpty() {
        me_refresh_layout.finishRefresh()
    }

    private fun setLoginState(data: UserBean?){
        data?.let {
            me_login_status_tv.text = getString(R.string.already_logged_in)
            GlideUtil.loadImage(mContext, dog_img_url,me_login_portrait_iv)
            me_login_username_tv.text = it.username
            me_login_nickname_tv.text = it.nickname
            me_refresh_layout.autoRefresh()
            me_logout_btn.visibility = View.VISIBLE
        }
    }

    private fun setLogoutState(){
        me_login_status_tv.text = getString(R.string.click_to_login)
        me_login_portrait_iv.setImageResource(R.mipmap.ic_launcher_round)
        me_login_username_tv.text = getString(R.string.dash)
        me_login_nickname_tv.text = ""
        me_login_level_tv.text = getString(R.string.dash)
        me_login_rank_tv.text = getString(R.string.dash)
        me_login_integral_tv.text = getString(R.string.dash)
        me_my_integral_num_tv.text = ""
        me_logout_btn.visibility = View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: LoginEvent?){
        event?.let {
            setLoginState(event.data)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.me_card_view -> if (!UserHelper.isLogin()) LoginActivity.start(mContext)
            R.id.me_logout_btn -> {
                showLogoutDialog()
            }
            R.id.me_my_integral_rl -> {
                if (UserHelper.isLogin()){
                    IntegralHistoryActivity.start(mContext)
                }else{
                    LoginActivity.start(mContext)
                }
            }
            R.id.me_rank_list_rl -> IntegralRankListActivity.start(mContext)
            R.id.me_my_collection_rl -> {
                if (UserHelper.isLogin()){
                    CollectActivity.start(mContext)
                }else{
                    LoginActivity.start(mContext)
                }
            }
            R.id.me_project_address_rl -> {
                val bean = BannerBean("",0,"",0,0,getString(R.string.project_address),0,"https://github.com/LZKDreamer/WanandroidKotlin")
                WebActivity.start(mContext,bean)
            }
            R.id.me_open_source_website_rl -> {
                val bean = BannerBean("",0,"",0,0,"WanAndroid",0,"https://www.wanandroid.com/")
                WebActivity.start(mContext,bean)
            }
            R.id.me_open_api_rl -> {
                val bean = BannerBean("",0,"",0,0,"玩Android开放API",0,"https://www.wanandroid.com/blog/show/2")
                WebActivity.start(mContext,bean)
            }
        }
    }

    private fun showLogoutDialog(){
        AlertDialog.Builder(mContext).apply {
            setTitle(R.string.tips)
            setMessage(R.string.logout_or_not)
            setNegativeButton(R.string.cancel){dialog: DialogInterface?, _: Int ->
                dialog?.dismiss()
            }
            setPositiveButton(R.string.confirm){dialog: DialogInterface?, _: Int ->
                dialog?.dismiss()
                LoadingDialog.show(childFragmentManager)
                mPresenter.requestLogout()
            }
        }.create().show()
    }
}
