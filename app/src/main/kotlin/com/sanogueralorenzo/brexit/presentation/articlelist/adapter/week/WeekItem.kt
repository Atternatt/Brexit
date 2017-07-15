package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week

import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import com.sanogueralorenzo.brexit.presentation.commons.adapter.AdapterConstants
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType

data class WeekItem(val articleList: ArrayList<ArticleItem> = ArrayList()) : ViewType {
    override fun getViewType() = AdapterConstants.WEEK
}

