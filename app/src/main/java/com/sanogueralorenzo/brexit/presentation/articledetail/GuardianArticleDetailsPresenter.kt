package com.sanogueralorenzo.brexit.presentation.articledetail

import com.sanogueralorenzo.brexit.domain.usecases.AddDeleteFavoriteArticleUseCase
import com.sanogueralorenzo.brexit.domain.usecases.CombineArticleDetailsIsFavoriteUseCase
import com.sanogueralorenzo.brexit.presentation.IView
import com.sanogueralorenzo.brexit.presentation.Presenter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import javax.inject.Inject

interface GuardianArticleDetailsView : IView {
    fun getArticleItem(): ArticleItem
    fun init()
    fun addBodyText(body: String)
    fun onError()
    fun favoriteAdded()
    fun favoriteDeleted()
    fun setFavoriteIcon(favorite: Boolean)
}

class GuardianArticleDetailsPresenter
@Inject constructor
(private val combineArticleDetailsIsFavoriteUseCase: CombineArticleDetailsIsFavoriteUseCase,
 private val addDeleteIsFavoriteUseCase: AddDeleteFavoriteArticleUseCase)
    : Presenter<GuardianArticleDetailsView>() {

    override fun attachView(view: GuardianArticleDetailsView) {
        super.attachView(view)
        if (view.getArticleItem().id != null) {
            val id = view.getArticleItem().id!!
            combineArticleDetailsIsFavoriteUseCase.articleId = id
            addDeleteIsFavoriteUseCase.articleId = id
        }
        getArticleDetails()
    }

    fun getArticleDetails() {
        addDisposable(combineArticleDetailsIsFavoriteUseCase.execute()
                .subscribe({
                    view?.addBodyText(it.articleBody)
                    view?.setFavoriteIcon(it.isFavorite)
                }, { view?.onError() }))
    }

    fun addDeleteFavorite() {
        addDisposable(addDeleteIsFavoriteUseCase.execute()
                .subscribe({
                    view?.setFavoriteIcon(it)
                    if (it) {
                        view?.favoriteAdded()
                    } else {
                        view?.favoriteDeleted()
                    }
                }, { view?.onError() }))
    }

}
