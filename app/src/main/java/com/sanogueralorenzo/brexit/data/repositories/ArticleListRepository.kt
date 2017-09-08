package com.sanogueralorenzo.brexit.data.repositories

import com.google.gson.reflect.TypeToken
import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.data.remote.ArticleListApi
import com.sanogueralorenzo.brexit.presentation.orderByDescendingWebPublicationDate
import io.reactivex.Observable
import javax.inject.Inject

class ArticleListRepository
@Inject constructor
(private val articleListApi: ArticleListApi,
 private val data: Local) {

    private val ARTICLES = "ARTICLES"

    fun getNetwork(): Observable<List<Result>> = articleListApi.searchArticles("brexit").map { it.response.results.orderByDescendingWebPublicationDate() }

    fun setData(articleList: List<Result>) = data.save(ARTICLES, articleList)

    fun getData(): Observable<List<Result>> {
        val articleList: List<Result>? = data.load(ARTICLES, object : TypeToken<List<Result>>() {}.type)
        if (articleList != null) {
            return Observable.just(articleList)
        }
        return Observable.empty()
    }
}
