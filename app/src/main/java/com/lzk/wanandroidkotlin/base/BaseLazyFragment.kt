package com.lzk.wanandroidkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/23
 * Function:
 */
abstract class BaseLazyFragment : Fragment() {
    protected lateinit var mContext: Context
    private var isFirstLoad = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad){
            onLazyInit()
            isFirstLoad = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = true
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView(view: View)

    abstract fun onLazyInit()
}