package com.sanogueralorenzo.brexit.domain.repositories

import com.google.gson.reflect.TypeToken
import com.sanogueralorenzo.brexit.data.local.SharedPreferencesManager
import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.data.remote.ArticleListApi
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class ArticleListRepository(private val articleListApi: ArticleListApi, private val sharedPreferencesManager: SharedPreferencesManager) {

    val ARTICLES = "ARTICLES"

    val ARTICLE_FAVORITE_LIST = "ARTICLE_FAVORITE_LIST"

    fun getArticleList(): Observable<List<Result>> = articleListApi.searchArticles("fintech,brexit")
            .map {
                Collections.sort(it.response.results, { o1: Result, o2: Result ->
                    -(o1.webPublicationDate?.compareTo(o2.webPublicationDate))!!
                })
                it.response.results
            }

    fun saveArticleList(articleList: List<Result>) = sharedPreferencesManager.saveObject(ARTICLES, articleList)

    fun getCacheArticleList(): List<Result> = sharedPreferencesManager.loadObject(ARTICLES, object : TypeToken<List<Result>>() {}.type) ?: ArrayList()

    fun deleteArticleList() = sharedPreferencesManager.delete(ARTICLES)

    fun getFavoriteArticleList(): List<String>? = sharedPreferencesManager.loadObject(ARTICLE_FAVORITE_LIST, object : TypeToken<List<String>>() {}.type) ?: ArrayList()

}

