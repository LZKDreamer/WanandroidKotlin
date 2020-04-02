package com.lzk.wanandroidkotlin.ui.tree.nav

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.NavigationBean
import com.lzk.wanandroidkotlin.ui.main.WebActivity
import com.lzk.wanandroidkotlin.utils.ColorUtil
import com.lzk.wanandroidkotlin.utils.htmlFormat
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
class NavigationAdapter(data: MutableList<NavigationBean>) : BaseQuickAdapter<NavigationBean,BaseViewHolder>(
    R.layout.item_system,data) {

    private var listener: OnTagClickListener? = null

    override fun convert(helper: BaseViewHolder, item: NavigationBean) {
        helper.setText(R.id.system_title,item.name)
        helper.getView<TagFlowLayout>(R.id.system_flow_layout).apply {
            adapter = object : TagAdapter<ArticleBean>(item.articles){
                override fun getView(parent: FlowLayout?, position: Int, t: ArticleBean?): View {
                    return LayoutInflater.from(parent?.context).inflate(R.layout.item_flow_layout,parent,false)
                        .findViewById<TextView>(R.id.flow_item_text).apply {
                            text = t?.title?.htmlFormat()
                            setTextColor(ColorUtil.randomColor())
                        }
                }
            }

            setOnTagClickListener { view, position, parent ->
                listener?.onTagClick(position,item.articles[position])
                true
            }
        }
    }

    interface OnTagClickListener{
        fun onTagClick(position: Int,data: ArticleBean)
    }

    fun setOnTagClickListener(listener: OnTagClickListener){
        this.listener =listener
    }
}