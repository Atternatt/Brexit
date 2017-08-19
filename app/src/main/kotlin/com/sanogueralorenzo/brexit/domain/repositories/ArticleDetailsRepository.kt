package com.sanogueralorenzo.brexit.domain.repositories

import com.google.gson.reflect.TypeToken
import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.data.remote.ArticleDetailsApi
import io.reactivex.Observable

class ArticleDetailsRepository(private val articleDetailsApi: ArticleDetailsApi, private val local: Local) {
    val ARTICLE = "ARTICLE_"

    val ARTICLE_FAVORITE_LIST = "ARTICLE_FAVORITE_LIST"

    fun getArticle(articleUrl: String): Observable<String> = articleDetailsApi.getArticle(articleUrl, "body").map { it.response.content.fields.body }

    fun saveArticle(articleUrl: String, body: String) = local.saveString(ARTICLE + articleUrl, body)

    fun getCacheArticle(articleUrl: String): String = local.loadString(ARTICLE + articleUrl)

    fun deleteArticle(articleUrl: String) = local.delete(ARTICLE + articleUrl)

    fun getFavoriteArticleList(): List<String> = local.load(ARTICLE_FAVORITE_LIST, object : TypeToken<List<String>>() {}.type) ?: ArrayList()

    fun saveFavoriteArticleList(favoriteArticleList: List<String>) = local.save(ARTICLE_FAVORITE_LIST, favoriteArticleList)
}