package com.lzk.wanandroidkotlin.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lzk.lib_common.utils.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed(
            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            },
            2000)
    }

    override fun onBackPressed() {

    }
}
