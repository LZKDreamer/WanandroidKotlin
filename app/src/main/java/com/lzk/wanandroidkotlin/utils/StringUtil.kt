package com.lzk.wanandroidkotlin.utils

import android.text.Html

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/26
 * Function:
 */

fun String.htmlFormat(): String{

    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this,Html.FROM_HTML_MODE_LEGACY).toString();
    } else {
        Html.fromHtml(this).toString();
    }
}