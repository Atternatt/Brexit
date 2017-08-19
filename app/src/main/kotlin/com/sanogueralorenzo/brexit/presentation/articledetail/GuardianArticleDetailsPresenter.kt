package com.sanogueralorenzo.brexit.presentation.articledetail

import com.sanogueralorenzo.brexit.domain.repositories.ArticleDetailsRepository
import com.sanogueralorenzo.brexit.presentation.IView
import com.sanogueralorenzo.brexit.presentation.Presenter
import com.sanogueralorenzo.brexit.presentation.articlelist.adapter.week.article.ArticleItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface GuardianArticleDetailsView : IView {
    fun getArticleItem(): ArticleItem
    fun init()
    fun addBodyText(body: String)
    fun onError()
    fun favoriteAdded()
    fun favoriteDeleted()
    fun setFavoriteIcon(favorite: Boolean)
}

class GuardianArticleDetailsPresenter(val articleDetailsRepository: ArticleDetailsRepository) : Presenter<GuardianArticleDetailsView>() {

    override fun attachView(view: GuardianArticleDetailsView) {
        super.attachView(view)
        getCacheArticle(view.getArticleItem().id!!)
        getArticle(view.getArticleItem().id!!)
        checkFavoriteArticle(view.getArticleItem().id!!)
    }

    fun getCacheArticle(articleUrl: String) {
        val articleBody = articleDetailsRepository.getCacheArticle(articleUrl)
        view?.addBodyText(articleBody)
    }

    fun getArticle(articleUrl: String) {
        addDisposable(articleDetailsRepository.getArticle(articleUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { save(articleUrl, it) }
                .subscribe({ view?.addBodyText(it) }, { view?.onError() }))
    }

    fun save(articleUrl: String, body: String) = articleDetailsRepository.saveArticle(articleUrl, body)

    fun saveFavorite(articleUrl: String) {
        val favoriteArticleUrlList = ArrayList<String>()
        favoriteArticleUrlList.addAll(articleDetailsRepository.getFavoriteArticleList())

        favoriteArticleUrlList
                .filter { it == articleUrl }
                .forEach {
                    favoriteArticleUrlList.remove(it)
                    articleDetailsRepository.saveFavoriteArticleList(favoriteArticleUrlList)
                    view?.setFavoriteIcon(false)
                    view?.favoriteDeleted()
                }

        favoriteArticleUrlList.add(articleUrl)
        articleDetailsRepository.saveFavoriteArticleList(favoriteArticleUrlList)
        view?.setFavoriteIcon(true)
        view?.favoriteAdded()
    }

    fun checkFavoriteArticle(articleUrl: String) {
        val favoriteArticleUrlList = ArrayList<String>()
        favoriteArticleUrlList.addAll(articleDetailsRepository.getFavoriteArticleList())
        favoriteArticleUrlList
                .filter { it == articleUrl }
                .forEach { view?.setFavoriteIcon(true) }
    }

}
