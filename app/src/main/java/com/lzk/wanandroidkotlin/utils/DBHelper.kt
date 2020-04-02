package com.lzk.wanandroidkotlin.utils

import com.lzk.wanandroidkotlin.api.model.SearchHistoryBean
import org.litepal.LitePal
import org.litepal.extension.find
import java.util.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/31
 * Function:
 */
object DBHelper {

    //查询搜索历史
    fun queryAllHistories(): List<SearchHistoryBean>?{
        return LitePal.order("date desc").find<SearchHistoryBean>()
    }

    //增加搜索历史
    fun addSearchHistory(key: String){
        val item = LitePal.where("key = ?",key).find<SearchHistoryBean>()
        if (item.isNullOrEmpty()){
            val bean = SearchHistoryBean()
            bean.key = key
            bean.date = Date().time
            bean.save()
        }else{
            item[0].date = Date().time
            item[0].save()
        }
    }

    //删除某个key
    fun deleteSingleKey(key: String){
        LitePal.deleteAll(SearchHistoryBean::class.java,"key = ?",key)
    }

    //删除所有历史记录
    fun deleteAllHistories(){
        LitePal.deleteAll(SearchHistoryBean::class.java)
    }

}