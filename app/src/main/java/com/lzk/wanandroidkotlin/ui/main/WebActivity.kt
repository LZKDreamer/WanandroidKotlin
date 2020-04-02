package com.lzk.wanandroidkotlin.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.BannerBean
import com.lzk.wanandroidkotlin.api.model.CategoryBean
import com.lzk.wanandroidkotlin.api.model.CollectBean
import com.lzk.wanandroidkotlin.base.BaseActivity
import com.lzk.wanandroidkotlin.utils.htmlFormat
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.io.Serializable


class WebActivity : BaseActivity() {

    private lateinit var mAgentWeb: AgentWeb

    companion object{
        const val INTENT_DATA = "intent_data"

        inline fun <reified T: Serializable>start(context: Context,data: T){
            context.startActivity(Intent(context as Activity,WebActivity::class.java).apply {
                putExtra(INTENT_DATA,data)
            })
        }
    }

    override fun getResId(): Int = R.layout.activity_web

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                if (!mAgentWeb.back()) finish()
            }
        }
        intent?.getSerializableExtra(INTENT_DATA).run{
            when(this){
                is ArticleBean -> {
                    supportActionBar?.title = title.htmlFormat()
                    initWebAgent(link)
                }
                is BannerBean -> {
                    supportActionBar?.title = title.htmlFormat()
                    initWebAgent(url)
                }
                is CollectBean -> {
                    supportActionBar?.title = title.htmlFormat()
                    initWebAgent(link)
                }
            }
        }


    }

    private fun initWebAgent(url: String){
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_container,LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}
