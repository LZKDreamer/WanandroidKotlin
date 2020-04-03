package com.lzk.wanandroidkotlin.ui.collect

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.api.model.CollectBean
import com.lzk.wanandroidkotlin.utils.GlideUtil
import com.lzk.wanandroidkotlin.utils.htmlFormat
import com.lzk.wanandroidkotlin.weight.CollectView
import java.util.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/26
 * Function:
 */
class CollectAdapter : BaseDelegateMultiAdapter<CollectBean,BaseViewHolder>() {

    private val TYPE_ARTICLE = 1
    private val TYPE_PROJECT = 2

    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<CollectBean>(){
            override fun getItemType(data: List<CollectBean>, position: Int): Int {
                return if (data[position].envelopePic.isNullOrBlank()) TYPE_ARTICLE else TYPE_PROJECT
            }
        })
        getMultiTypeDelegate()
            ?.addItemType(TYPE_ARTICLE, R.layout.item_article)
            ?.addItemType(TYPE_PROJECT,R.layout.item_project)

        addChildClickViewIds(R.id.article_collect_iv)
        addChildClickViewIds(R.id.project_collect_iv)
    }

    override fun convert(helper: BaseViewHolder, item: CollectBean) {
        when(helper.itemViewType){
            TYPE_ARTICLE -> {
                item.apply {
                    helper.setText(R.id.article_author_tv,if (author.isEmpty()) "匿名用户" else author)
                    helper.setText(R.id.article_time_tv,niceDate)
                    helper.setText(R.id.article_title_tv,title.htmlFormat())
                    helper.setText(R.id.article_category_tv,chapterName)
                    helper.getView<ImageView>(R.id.article_collect_iv).setImageResource(R.drawable.ic_collect)
                    //tag
                    helper.setGone(R.id.article_new_tv,true)
                    helper.setGone(R.id.article_top_tv,true)
                    helper.setGone(R.id.article_tag_tv,true)
                }

            }
            TYPE_PROJECT -> {
                item.apply {
                    helper.setText(R.id.project_author_tv,if (author.isEmpty()) "匿名用户" else author)
                    helper.setText(R.id.project_time_tv,niceDate)
                    helper.setText(R.id.project_content_tv,desc.htmlFormat())
                    helper.setText(R.id.project_category_tv,chapterName)
                    helper.setText(R.id.project_title_tv,title.htmlFormat())
                    GlideUtil.loadImage(context,envelopePic,helper.getView(R.id.project_image_iv))
                    helper.getView<ImageView>(R.id.project_collect_iv).setImageResource(R.drawable.ic_collect)
                    //tag
                    helper.setGone(R.id.project_new_tv,true)
                    helper.setGone(R.id.project_top_tv,true)
                    helper.setGone(R.id.project_tag_tv,true)
                }
            }
        }
    }
}