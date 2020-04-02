package com.lzk.wanandroidkotlin.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.eventbus.EventBusUtil
import com.lzk.wanandroidkotlin.eventbus.LoginEvent
import com.lzk.wanandroidkotlin.eventbus.RegisterEvent
import com.lzk.wanandroidkotlin.weight.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginActivity : BaseMVPActivity<LoginContract.View,LoginPresenter>(),LoginContract.View,View.OnClickListener {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun getResId(): Int = R.layout.activity_login

    override fun isRegisterEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login_btn.setOnClickListener(this)
        login_register_btn.setOnClickListener(this)
        login_toolbar.apply {
            setNavigationIcon(R.drawable.ic_cancel)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun requestLoginSuccess(data: UserBean) {
        UserHelper.saveUserInfo(data)
        LoadingDialog.dismiss()
        EventBusUtil.sendEvent(LoginEvent(data))
    }

    override fun showLoading() {

    }

    override fun showError(msg: String, type: Int) {
        LoadingDialog.dismiss()
        showToast(msg)
    }

    override fun showEmpty() {
        LoadingDialog.dismiss()
        showToast(getString(R.string.login_failed))
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_btn -> {
                when{
                    login_username_et.text!!.trim().isBlank() || login_password_et.text!!.trim().isBlank() ->{
                        showToast(getString(R.string.username_or_psw_can_not_be_empty))
                    }
                    else -> {
                        LoadingDialog.show(supportFragmentManager)
                        mPresenter.requestLogin(login_username_et.text!!.trim().toString()
                            ,login_password_et.text!!.trim().toString())
                    }
                }
            }
            R.id.login_register_btn -> RegisterActivity.start(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: LoginEvent?){
        finish()
    }
}
