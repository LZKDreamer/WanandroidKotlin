package com.lzk.wanandroidkotlin.ui.integral

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.IntegralHistoryBean

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
class IntegralHistoryAdapter(data: MutableList<IntegralHistoryBean>) : BaseQuickAdapter<IntegralHistoryBean,BaseViewHolder>(R.layout.item_integral_history,data) {
    override fun convert(helper: BaseViewHolder, item: IntegralHistoryBean) {
        val descArray = item.desc.split(",")
        helper.setText(R.id.item_integral_history_desc_tv,descArray[1].trim())
        helper.setText(R.id.item_integral_history_date_tv,descArray[0].trim())
        helper.setText(R.id.item_integral_history_coin_tv,"+${item.coinCount}")
    }
}