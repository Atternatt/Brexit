package com.sanogueralorenzo.brexit.presentation.articlelist

import com.sanogueralorenzo.brexit.presentation.IView
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType

interface GuardianArticleListView : IView {

    fun addItemList(itemList: List<ViewType>)

    fun onError()

}