package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.dayMonthYearFormat
import com.sanogueralorenzo.brexit.presentation.inflate
import com.sanogueralorenzo.brexit.presentation.loadUrlRound
import kotlinx.android.synthetic.main.list_item_article.view.*
import java.io.Serializable
import java.util.*

data class ArticleItem(val id: String?, val title: String?, val url: String?, val date: Date?) : Serializable

interface OnArticleClickListener {
    fun onArticleClick(item: ArticleItem)
}

class ArticleAdapter(val listener: OnArticleClickListener, val items: ArrayList<ArticleItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ArticleViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as ArticleViewHolder).bind(items[position])

    inner class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_article)) {

        fun bind(item: ArticleItem) = with(itemView) {
            articleImageView.loadUrlRound(item.url)
            articleTitleTextView.setText(item.title ?: resources.getString(R.string.no_article_title))
            articleDateTextView.setText(item.date?.dayMonthYearFormat() ?: resources.getString(R.string.no_article_date))
            itemView.setOnClickListener { listener.onArticleClick(item) }
        }
    }
}