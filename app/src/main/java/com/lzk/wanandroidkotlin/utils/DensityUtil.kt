package com.lzk.wanandroidkotlin.utils

import android.content.Context
import android.util.TypedValue

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
object DensityUtil {
    fun dp2px(context: Context,dp: Float)
            = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.resources.displayMetrics)

    fun sp2px(context: Context,sp: Float)
            = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.resources.displayMetrics)


}