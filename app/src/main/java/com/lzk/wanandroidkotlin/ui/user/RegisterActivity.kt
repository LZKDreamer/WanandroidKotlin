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
import com.lzk.wanandroidkotlin.eventbus.LogoutEvent
import com.lzk.wanandroidkotlin.eventbus.RegisterEvent
import com.lzk.wanandroidkotlin.weight.LoadingDialog
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMVPActivity<RegisterContract.View,RegisterPresenter>(),RegisterContract.View,View.OnClickListener{

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,RegisterActivity::class.java))
        }
    }

    override fun createPresenter(): RegisterPresenter = RegisterPresenter()

    override fun getResId(): Int = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        register_toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
        register_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_btn -> {
                val username = register_username_et.text!!.trim().toString()
                val password = register_password_et.text!!.trim().toString()
                val repassword = register_repeat_password_et.text!!.trim().toString()
                when{
                    username.isEmpty() ||
                            password.isEmpty() ||
                            repassword.isEmpty() -> showToast(getString(R.string.username_or_psw_can_not_be_empty))
                    password != repassword -> showToast(getString(R.string.password_is_identical))
                    else -> {
                        LoadingDialog.show(supportFragmentManager)
                        mPresenter.requestRegister(username, password, repassword)
                    }
                }
            }
        }
    }

    override fun requestRegisterSuccess(data: UserBean) {
        UserHelper.saveUserInfo(data)
        LoadingDialog.dismiss()
        EventBusUtil.sendEvent(LoginEvent(data))
        finish()
    }

    override fun showLoading() {

    }

    override fun showError(msg: String, type: Int) {
        LoadingDialog.dismiss()
        showToast(msg)
    }

    override fun showEmpty() {
        LoadingDialog.dismiss()
        showToast(getString(R.string.register_failed))
    }
}
