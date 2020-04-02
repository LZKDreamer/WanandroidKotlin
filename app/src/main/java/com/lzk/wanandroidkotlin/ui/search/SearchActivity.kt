package com.lzk.wanandroidkotlin.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.HotKeyBean
import com.lzk.wanandroidkotlin.api.model.SearchHistoryBean
import com.lzk.wanandroidkotlin.base.BaseMVPActivity
import com.lzk.wanandroidkotlin.utils.ColorUtil
import com.lzk.wanandroidkotlin.utils.DBHelper
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseMVPActivity<SearchContract.View,SearchPresenter>(),SearchContract.View {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,SearchActivity::class.java))
            context as Activity
            context.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }

    private var mHistoryAdapter: SearchHistoryAdapter? = null
    private var mHistoryList = mutableListOf<SearchHistoryBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search_toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }

        mHistoryAdapter = SearchHistoryAdapter(mHistoryList.map{ it.key }.toMutableList()).apply {

            setOnItemClickListener { adapter, view, position ->
                SearchResultActivity.start(this@SearchActivity,data[position])
            }

            setOnItemChildClickListener { adapter, view, position ->
                DBHelper.deleteSingleKey(adapter.data[position] as String)
                mHistoryAdapter?.remove(position)
            }
        }

        search_rv.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = mHistoryAdapter
        }
        mHistoryAdapter?.setEmptyView(R.layout.search_empty_view)

        search_clear_tv.setOnClickListener {
            mHistoryAdapter?.let {
                if (it.data.isNotEmpty()){
                    DBHelper.deleteAllHistories()
                    mHistoryList.clear()
                    mHistoryAdapter?.setNewData(mHistoryList.map { item -> item.key }.toMutableList())
                }
            }
        }

        mPresenter.requestHotKey()
        mPresenter.requestSearchHistory()

    }

    override fun createPresenter(): SearchPresenter = SearchPresenter()

    override fun getResId(): Int = R.layout.activity_search

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_menu_btn -> {
                search_et.text.toString().trim().let {
                    if (it.isNotEmpty()){
                        SearchResultActivity.start(this,it)
                        DBHelper.addSearchHistory(it)
                        mPresenter.requestSearchHistory()
                    }
                }
            }
        }
        return true
    }

    override fun requestHotKeySuccess(data: MutableList<HotKeyBean>) {
        search_flow_layout.apply {
            adapter = object : TagAdapter<HotKeyBean>(data){
                override fun getView(parent: FlowLayout, position: Int, t: HotKeyBean): View {
                    val textView: TextView = LayoutInflater.from(parent.context).inflate(R.layout.item_flow_layout,parent,false) as TextView
                    textView.text = t.name
                    textView.setTextColor(ColorUtil.randomColor())
                    return textView
                }
            }.apply {
                setOnTagClickListener { view, position, parent ->
                    SearchResultActivity.start(this@SearchActivity,data[position].name)
                    DBHelper.addSearchHistory(data[position].name)
                    mPresenter.requestSearchHistory()
                    true
                }
            }
        }
    }

    override fun requestSearchHistorySuccess(data: List<SearchHistoryBean>?) {
        data?.let {
            mHistoryList.clear()
            mHistoryList.addAll(data)
            mHistoryAdapter?.setNewData(mHistoryList.map { it.key }.toMutableList())
        }
    }

    override fun showLoading() {

    }

    override fun showError(msg: String, type: Int) {

    }

    override fun showEmpty() {

    }
}
