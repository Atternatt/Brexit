package com.sanogueralorenzo.brexit.presentation.articlelist.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.header.HeaderDelegateAdapter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.WeekDelegateAdapter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.WeekItem
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.OnArticleClickListener
import com.sanogueralorenzo.brexit.presentation.commons.adapter.AdapterConstants
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewTypeDelegateAdapter

class GuardianAdapter(onArticleClickListener: OnArticleClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType> = ArrayList()
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(AdapterConstants.WEEK, WeekDelegateAdapter(onArticleClickListener))
        delegateAdapters.put(AdapterConstants.HEADER, HeaderDelegateAdapter())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

    fun addItemList(itemList: List<ViewType>) {
        items.addAll(itemList)
    }

    fun clear() {
        items.clear()
    }

    fun getPostItemList(): List<WeekItem> {
        return items
                .filter { it.getViewType() == AdapterConstants.WEEK }
                .map { it as WeekItem }
    }
}