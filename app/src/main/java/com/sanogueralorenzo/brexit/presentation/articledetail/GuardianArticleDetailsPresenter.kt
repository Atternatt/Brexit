package com.sanogueralorenzo.brexit.presentation.articledetail

import com.sanogueralorenzo.brexit.domain.usecases.AddDeleteFavoriteArticleUseCase
import com.sanogueralorenzo.brexit.domain.usecases.CombineArticleDetailsIsFavoriteUseCase
import com.sanogueralorenzo.brexit.presentation.IView
import com.sanogueralorenzo.brexit.presentation.Presenter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import java.lang.ref.WeakReference
import javax.inject.Inject

interface GuardianArticleDetailsView : IView {
    val articleItem: ArticleItem
    fun init()
    fun addBodyText(body: String)
    fun onError()
    fun favoriteAdded()
    fun favoriteDeleted()
    fun setFavoriteIcon(favorite: Boolean)
}

class GuardianArticleDetailsPresenter
private constructor
(private val combineArticleDetailsIsFavoriteUseCase: CombineArticleDetailsIsFavoriteUseCase,
 private val addDeleteIsFavoriteUseCase: AddDeleteFavoriteArticleUseCase,
 override val view: WeakReference<GuardianArticleDetailsView>)
    : Presenter<GuardianArticleDetailsView>() {

    class Factory
    @Inject constructor(private val combineArticleDetailsIsFavoriteUseCase: CombineArticleDetailsIsFavoriteUseCase,
                        private val addDeleteIsFavoriteUseCase: AddDeleteFavoriteArticleUseCase) {

        private var presenter: GuardianArticleDetailsPresenter? = null

        fun create(view: GuardianArticleDetailsView): GuardianArticleDetailsPresenter =
                presenter ?: GuardianArticleDetailsPresenter(combineArticleDetailsIsFavoriteUseCase,
                        addDeleteIsFavoriteUseCase,
                        WeakReference(view)).apply { presenter = this }
    }

    override fun attachView(view: GuardianArticleDetailsView) {

    }

    private fun getArticleDetails() {
        addDisposable(combineArticleDetailsIsFavoriteUseCase.execute()
                .subscribe({
                    view.get()?.addBodyText(it.articleBody)
                    view.get()?.setFavoriteIcon(it.isFavorite)
                }, { view.get()?.onError() }))
    }

    fun addDeleteFavorite() {
        addDisposable(addDeleteIsFavoriteUseCase.execute()
                .subscribe({
                    view.get()?.setFavoriteIcon(it)
                    if (it) {
                        view.get()?.favoriteAdded()
                    } else {
                        view.get()?.favoriteDeleted()
                    }
                }, { view.get()?.onError() }))
    }

    init {
        view.get()?.articleItem?.id?.let {
            combineArticleDetailsIsFavoriteUseCase.articleId = it
            addDeleteIsFavoriteUseCase.articleId = it
        }
        getArticleDetails()
    }
}
