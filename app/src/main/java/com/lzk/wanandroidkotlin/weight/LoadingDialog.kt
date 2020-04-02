package com.lzk.wanandroidkotlin.weight

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lzk.wanandroidkotlin.R

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/30
 * Function:
 */
class LoadingDialog() : DialogFragment() {
    companion object{
        private const val TAG = "LOADING_DIALOG"
        private val mLoadingDialog: LoadingDialog by lazy {
            LoadingDialog()
        }

        fun show(fm: FragmentManager){
            if (!mLoadingDialog.isAdded) mLoadingDialog.show(fm, TAG)
        }

        fun dismiss(){
            mLoadingDialog.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

}