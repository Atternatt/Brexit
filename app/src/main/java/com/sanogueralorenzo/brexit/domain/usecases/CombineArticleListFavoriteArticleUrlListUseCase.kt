package com.sanogueralorenzo.brexit.domain.usecases

import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.data.repositories.ArticleListRepository
import com.sanogueralorenzo.brexit.data.repositories.FavoriteArticleIdListRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class ArticlesFavoriteArticleIdsLists(val articleList: List<Result>, val favoriteArticleIdList:List<String>)

class CombineArticleListFavoriteArticleUrlListUseCase
@Inject constructor
(private val articleListRepository: ArticleListRepository,
 private val favoriteArticleIdListRepository: FavoriteArticleIdListRepository)
    : UseCase<ArticlesFavoriteArticleIdsLists> {

    override fun execute(): Observable<ArticlesFavoriteArticleIdsLists> = Observable.concat(data(), network())

    private fun data(): Observable<ArticlesFavoriteArticleIdsLists> {
        return Observables.zip(articleListRepository.getData(), favoriteArticleIdListRepository.getData(),
                { articleList:List<Result>, favoriteArticleIdList: List<String> ->
                    ArticlesFavoriteArticleIdsLists(articleList, favoriteArticleIdList)
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun network(): Observable<ArticlesFavoriteArticleIdsLists> {
        return Observables.zip(articleListRepository.getNetwork(), favoriteArticleIdListRepository.getData(),
                { articleList:List<Result>, favoriteArticleIdList: List<String> ->
                    ArticlesFavoriteArticleIdsLists(articleList, favoriteArticleIdList)
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    articleListRepository.setData(it.articleList)
                }
    }
}