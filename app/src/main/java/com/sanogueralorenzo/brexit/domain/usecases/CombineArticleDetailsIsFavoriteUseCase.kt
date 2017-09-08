package com.sanogueralorenzo.brexit.domain.usecases

import com.sanogueralorenzo.brexit.data.repositories.ArticleDetailsRepository
import com.sanogueralorenzo.brexit.data.repositories.FavoriteArticleIdListRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class ArticlesDetailsIsFavorite(val articleBody: String, val isFavorite: Boolean)

class CombineArticleDetailsIsFavoriteUseCase
@Inject constructor
(private val articleDetailsRepository: ArticleDetailsRepository,
 private val favoriteArticleIdListRepository: FavoriteArticleIdListRepository)
    : UseCase<ArticlesDetailsIsFavorite> {

    var articleId = ""

    override fun execute(): Observable<ArticlesDetailsIsFavorite> = Observable.concat(data(), network())

    private fun data(): Observable<ArticlesDetailsIsFavorite> {
        return Observables.zip(articleDetailsRepository.getData(articleId), favoriteArticleIdListRepository.getData(),
                { articleBody: String, favoriteArticleUrlList: List<String> ->
                    ArticlesDetailsIsFavorite(articleBody, favoriteArticleUrlList.contains(articleId))
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun network(): Observable<ArticlesDetailsIsFavorite> {
        return Observables.zip(articleDetailsRepository.getNetwork(articleId), favoriteArticleIdListRepository.getData(),
                { articleBody: String, favoriteArticleUrlList: List<String> ->
                    ArticlesDetailsIsFavorite(articleBody, favoriteArticleUrlList.contains(articleId))
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    articleDetailsRepository.setData(articleId, it.articleBody)
                }
    }
}