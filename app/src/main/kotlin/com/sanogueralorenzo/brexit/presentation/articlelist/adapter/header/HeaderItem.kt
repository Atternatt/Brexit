package com.sanogueralorenzo.brexit.presentation.articlelist.adapter.header

import com.sanogueralorenzo.brexit.presentation.commons.adapter.AdapterConstants
import com.sanogueralorenzo.brexit.presentation.commons.adapter.ViewType

data class HeaderItem(val text: String?) : ViewType {
    override fun getViewType() = AdapterConstants.HEADER
}

