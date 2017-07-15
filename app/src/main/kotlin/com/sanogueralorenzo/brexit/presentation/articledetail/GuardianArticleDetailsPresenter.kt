package com.sanogueralorenzo.brexit.presentation.articledetail

import com.sanogueralorenzo.brexit.domain.repositories.ArticleDetailsRepository
import com.sanogueralorenzo.brexit.presentation.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GuardianArticleDetailsPresenter(val articleDetailsRepository: ArticleDetailsRepository) : Presenter<GuardianArticleDetailsView>() {

    private var disposable: Disposable? = null

    fun getCacheArticle(articleUrl: String) {
        val articleBody = articleDetailsRepository.getCacheArticle(articleUrl)
        view?.addBodyText(articleBody)
    }

    fun getArticle(articleUrl: String) {
        disposable = articleDetailsRepository.getArticle(articleUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { save(articleUrl, it) }
                .subscribe({ view?.addBodyText(it) }, { view?.onError() })
    }

    fun save(articleUrl: String, body: String) {
        articleDetailsRepository.saveArticle(articleUrl, body)
    }

    fun saveFavorite(articleUrl: String) {
        val favoriteArticleUrlList = ArrayList<String>()
        favoriteArticleUrlList.addAll(articleDetailsRepository.getFavoriteArticleList() ?: ArrayList<String>())

        favoriteArticleUrlList
                .filter { it == articleUrl }
                .forEach {
                    favoriteArticleUrlList.remove(it)
                    articleDetailsRepository.saveFavoriteArticleList(favoriteArticleUrlList)
                    view?.setFavoriteIcon(false)
                    view?.favoriteDeleted()
                    return
                }

        favoriteArticleUrlList.add(articleUrl)
        articleDetailsRepository.saveFavoriteArticleList(favoriteArticleUrlList)
        view?.setFavoriteIcon(true)
        view?.favoriteAdded()
    }

    fun checkFavoriteArticle(articleUrl: String) {
        val favoriteArticleUrlList = ArrayList<String>()
        favoriteArticleUrlList.addAll(articleDetailsRepository.getFavoriteArticleList() ?: ArrayList<String>())
        favoriteArticleUrlList
                .filter { it == articleUrl }
                .forEach { view?.setFavoriteIcon(true) }
    }

    fun dispose() {
        disposable?.dispose()
    }

}
