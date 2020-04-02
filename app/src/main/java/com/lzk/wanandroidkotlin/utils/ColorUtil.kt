package com.lzk.wanandroidkotlin.utils

import android.graphics.Color
import java.util.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/27
 * Function:
 */
object ColorUtil {
    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        Random().run {
            //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
            val red = nextInt(190)
            val green = nextInt(190)
            val blue = nextInt(190)
            //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
            return Color.rgb(red, green, blue)
        }
    }
}