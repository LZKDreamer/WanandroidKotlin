package com.lzk.wanandroidkotlin.ui.search

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
class SearchHistoryAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.item_search_history,data) {

    init {
        addChildClickViewIds(R.id.search_history_item_delete_iv)
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.search_history_item_tv,item)
    }

}