package com.sanogueralorenzo.brexit.domain.repositories

import com.google.gson.reflect.TypeToken
import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.data.model.ArticleListResponse
import com.sanogueralorenzo.brexit.data.model.Result
import com.sanogueralorenzo.brexit.data.remote.ArticleListApi
import io.reactivex.Observable

class ArticleListRepository(private val articleListApi: ArticleListApi, private val local: Local) {
    val ARTICLES = "ARTICLES"

    val ARTICLE_FAVORITE_LIST = "ARTICLE_FAVORITE_LIST"

    fun getArticleList(): Observable<ArticleListResponse> = articleListApi.searchArticles("brexit")

    fun saveArticleList(articleList: List<Result>) = local.save(ARTICLES, articleList)

    fun getCacheArticleList(): List<Result> = local.load(ARTICLES, object : TypeToken<List<Result>>() {}.type) ?: ArrayList()

    fun deleteArticleList() = local.delete(ARTICLES)

    fun getFavoriteArticleList(): List<String>? = local.load(ARTICLE_FAVORITE_LIST, object : TypeToken<List<String>>() {}.type) ?: ArrayList()
}