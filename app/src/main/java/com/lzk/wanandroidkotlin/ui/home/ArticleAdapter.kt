package com.lzk.wanandroidkotlin.ui.home

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lzk.wanandroidkotlin.R
import com.lzk.wanandroidkotlin.api.model.ArticleBean
import com.lzk.wanandroidkotlin.utils.GlideUtil
import com.lzk.wanandroidkotlin.utils.htmlFormat
import com.lzk.wanandroidkotlin.weight.CollectView
import java.util.*

/**
 * Author: LiaoZhongKai.
 * Date: 2020/3/26
 * Function:
 */
class ArticleAdapter(val showTag: Boolean = false) : BaseDelegateMultiAdapter<ArticleBean,BaseViewHolder>() {

    private val TYPE_ARTICLE = 1
    private val TYPE_PROJECT = 2

    init {
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ArticleBean>(){
            override fun getItemType(data: List<ArticleBean>, position: Int): Int {
                return if (data[position].envelopePic.isNullOrBlank()) TYPE_ARTICLE else TYPE_PROJECT
            }
        })
        getMultiTypeDelegate()
            ?.addItemType(TYPE_ARTICLE, R.layout.item_article)
            ?.addItemType(TYPE_PROJECT,R.layout.item_project)

        addChildClickViewIds(R.id.article_collect_iv)
        addChildClickViewIds(R.id.project_collect_iv)
    }

    override fun convert(helper: BaseViewHolder, item: ArticleBean) {
        when(helper.itemViewType){
            TYPE_ARTICLE -> {
                item.apply {
                    helper.setText(R.id.article_author_tv,if (TextUtils.isEmpty(author)) shareUser else author)
                    helper.setText(R.id.article_time_tv,niceDate)
                    helper.setText(R.id.article_title_tv,title.htmlFormat())
                    helper.setText(R.id.article_category_tv,"$superChapterName/$chapterName")
                    if (collect){
                        helper.getView<ImageView>(R.id.article_collect_iv).setImageResource(R.drawable.ic_collect)
                    }else{
                        helper.getView<ImageView>(R.id.article_collect_iv).setImageResource(R.drawable.ic_uncollect)
                    }
                    //tag
                    if (showTag){
                        helper.setGone(R.id.article_new_tv,!fresh)
                        helper.setGone(R.id.article_top_tv,type != 1)
                        if (tags.isNullOrEmpty()){
                            helper.setGone(R.id.article_tag_tv,true)
                        }else{
                            helper.setGone(R.id.article_tag_tv,false)
                            helper.setText(R.id.article_tag_tv,tags[0].name)
                        }
                    }else{
                        helper.setGone(R.id.article_new_tv,true)
                        helper.setGone(R.id.article_top_tv,true)
                        helper.setGone(R.id.article_tag_tv,true)
                    }
                }

            }
            TYPE_PROJECT -> {
                item.apply {
                    helper.setText(R.id.project_author_tv,if (author.isNotBlank())author.htmlFormat() else shareUser.htmlFormat())
                    helper.setText(R.id.project_time_tv,niceDate)
                    helper.setText(R.id.project_content_tv,desc.htmlFormat())
                    helper.setText(R.id.project_category_tv,"$superChapterName/$chapterName")
                    helper.setText(R.id.project_title_tv,title.htmlFormat())
                    GlideUtil.loadImage(context,envelopePic,helper.getView(R.id.project_image_iv))
                    if (collect){
                        helper.getView<ImageView>(R.id.project_collect_iv).setImageResource(R.drawable.ic_collect)
                    }else{
                        helper.getView<ImageView>(R.id.project_collect_iv).setImageResource(R.drawable.ic_uncollect)
                    }
                    //tag
                    if (showTag){
                        helper.setGone(R.id.project_new_tv,!fresh)
                        helper.setGone(R.id.project_top_tv,type != 1)
                        if (tags.isNullOrEmpty()){
                            helper.setGone(R.id.project_tag_tv,true)
                        }else{
                            helper.setGone(R.id.project_tag_tv,false)
                            helper.setText(R.id.project_tag_tv,tags[0].name)
                        }
                    }else{
                        helper.setGone(R.id.project_new_tv,true)
                        helper.setGone(R.id.project_top_tv,true)
                        helper.setGone(R.id.project_tag_tv,true)
                    }
                }
            }
        }
    }
}