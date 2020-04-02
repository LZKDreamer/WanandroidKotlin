package com.lzk.wanandroidkotlin.ui.user

import com.lzk.wanandroidkotlin.api.model.IntegralBean
import com.lzk.wanandroidkotlin.api.model.UserBean
import com.lzk.wanandroidkotlin.utils.SPUtil

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/29
 * Function:
 */
object UserHelper {

    private const val SP_USER = "SP_USER"
    private const val SP_INTEGRAL = "SP_INTEGRAL"

    fun isLogin(): Boolean{
        return SPUtil.mInstance.getObject(
            SP_USER,UserBean::class.java) != null
    }

    fun saveUserInfo(data: UserBean){
        SPUtil.mInstance.putObject(
            SP_USER,data)
    }

    fun getUserInfo(): UserBean?{
        return SPUtil.mInstance.getObject(
            SP_USER,UserBean::class.java)
    }

    fun clearUserInfo(){
        SPUtil.mInstance.remove(
            SP_USER
        )
        SPUtil.mInstance.remove(
            SP_INTEGRAL
        )
    }

    fun getIntegral(): IntegralBean?{
        return SPUtil.mInstance.getObject(
            SP_INTEGRAL,IntegralBean::class.java)
    }

    fun saveIntegral(data: IntegralBean){
        SPUtil.mInstance.putObject(
            SP_INTEGRAL,data)
    }
}