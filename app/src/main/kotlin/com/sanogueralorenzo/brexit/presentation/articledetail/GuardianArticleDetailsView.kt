package com.sanogueralorenzo.brexit.presentation.articledetail

import com.sanogueralorenzo.brexit.presentation.IView

interface GuardianArticleDetailsView : IView {

    fun addBodyText(body: String)

    fun onError()

    fun favoriteAdded()

    fun favoriteDeleted()

    fun setFavoriteIcon(favorite: Boolean)

}