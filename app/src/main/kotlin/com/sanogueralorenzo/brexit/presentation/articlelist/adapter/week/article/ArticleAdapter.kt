package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.dayMonthYearFormat
import com.sanogueralorenzo.brexit.presentation.inflate
import com.sanogueralorenzo.brexit.presentation.loadUrlRound
import kotlinx.android.synthetic.main.list_item_article.view.*
import java.util.*

class ArticleAdapter(val listener: OnArticleClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ArticleItem> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleViewHolder).bind(items[position])
    }

    fun addItemList(itemList: List<ArticleItem>) {
        items.addAll(itemList)
    }

    inner class ArticleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_article)) {

        fun bind(item: ArticleItem) = with(itemView) {
            articleImageView.loadUrlRound(item.url)
            articleTitleTextView.setText(item.title ?: resources.getString(R.string.no_article_title))
            articleDateTextView.setText(item.date?.dayMonthYearFormat() ?: resources.getString(R.string.no_article_date))
            super.itemView.setOnClickListener { listener.onArticleClick(item) }
        }
    }
}