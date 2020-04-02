package com.lzk.lib_common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */

inline fun <reified T: Activity>Context.startActivity(){
    startActivity(Intent(this,T::class.java))
}