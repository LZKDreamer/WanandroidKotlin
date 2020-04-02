package com.lzk.wanandroidkotlin.weight

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import androidx.appcompat.widget.AppCompatImageView
import com.lzk.wanandroidkotlin.R
import java.util.jar.Attributes


/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/26
 * Function:
 */
class CollectView : AppCompatImageView{

    private lateinit var animator: AnimatorSet

    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet){
        init()
    }

    private fun init(){
        val x: ObjectAnimator = ObjectAnimator.ofFloat(this,View.SCALE_X,1f,0.7f,1f)
        val y: ObjectAnimator = ObjectAnimator.ofFloat(this,View.SCALE_Y,1f,0.7f,1f)
        animator = AnimatorSet().apply {
            duration = 300
            interpolator = AccelerateInterpolator()
            playTogether(x,y)
        }
    }

    fun changeStatus(collect: Boolean){
        if (collect) setImageResource(R.drawable.ic_collect) else setImageResource(R.drawable.ic_uncollect)
        animator.cancel()
        animator.start()

    }

}