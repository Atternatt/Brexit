package com.sanogueralorenzo.brexit.presentation.commons

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

object AdapterConstants {
    val WEEK = 1
    val HEADER = 2
}

interface ViewType {
    fun getViewType(): Int
}

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}
