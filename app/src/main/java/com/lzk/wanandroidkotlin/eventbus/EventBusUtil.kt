package com.lzk.wanandroidkotlin.eventbus

import android.content.Context
import com.lzk.wanandroidkotlin.eventbus.Event
import org.greenrobot.eventbus.EventBus

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/30
 * Function:
 */
object EventBusUtil {

    fun register(target: Any){
        EventBus.getDefault().register(target)
    }

    fun unregister(target: Any){
        EventBus.getDefault().unregister(target)
    }

    fun sendEvent(event: Any?){
        EventBus.getDefault().post(event)
    }
}