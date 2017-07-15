package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleAdapter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.OnArticleClickListener
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewTypeDelegateAdapter
import com.sanogueralorenzo.brexit.presentation.inflate
import kotlinx.android.synthetic.main.list_item_week.view.*

class WeekDelegateAdapter(val onArticleClickListener: OnArticleClickListener) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = WeekViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) = (holder as WeekViewHolder).bind(item as WeekItem)

    inner class WeekViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_week)) {

        fun bind(item: WeekItem) = with(itemView) {
            articleRecyclerView.apply {
                setHasFixedSize(true)
                val linearLayout = LinearLayoutManager(context)
                layoutManager = linearLayout
            }
            articleRecyclerView.adapter = ArticleAdapter(onArticleClickListener)
            (articleRecyclerView.adapter as ArticleAdapter).addItemList(item.articleList)
            (articleRecyclerView.adapter as ArticleAdapter).notifyDataSetChanged()
        }
    }
}