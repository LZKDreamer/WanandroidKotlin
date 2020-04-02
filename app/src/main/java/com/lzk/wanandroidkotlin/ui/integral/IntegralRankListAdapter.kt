package com.lzk.wanandroidkotlin.ui.integral

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.IntegralBean

/**
 * Author: LiaoZhongKai.
 * Date: 2020/4/2
 * Function:
 */
class IntegralRankListAdapter(data: MutableList<IntegralBean>) : BaseQuickAdapter<IntegralBean,BaseViewHolder>(R.layout.item_integral_rank_list,data) {
    override fun convert(helper: BaseViewHolder, item: IntegralBean) {
        helper.setText(R.id.item_integral_rank_level_tv,item.rank.toString())
        helper.setText(R.id.item_integral_rank_username_tv,item.username)
        helper.setText(R.id.item_integral_rank_coin_tv,item.coinCount.toString())
    }
}