package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.header

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.sanogueralorenzo.brexit.R
import com.sanogueralorenzo.brexit.presentation.commons.AdapterConstants
import com.sanogueralorenzo.brexit.presentation.commons.ViewType
import com.sanogueralorenzo.brexit.presentation.commons.ViewTypeDelegateAdapter
import com.sanogueralorenzo.brexit.presentation.inflate
import kotlinx.android.synthetic.main.list_item_header.view.*

data class HeaderItem(val text: String?) : ViewType {
    override fun getViewType() = AdapterConstants.HEADER
}

class HeaderDelegateAdapter() : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = HeaderViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) = (holder as HeaderViewHolder).bind(item as HeaderItem)

    inner class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_item_header)) {

        fun bind(item: HeaderItem) = with(itemView) {
            headerTextView.text = item.text
        }
    }
}
