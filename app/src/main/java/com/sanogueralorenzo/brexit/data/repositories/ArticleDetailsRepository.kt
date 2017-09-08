package com.sanogueralorenzo.brexit.data.repositories

import com.sanogueralorenzo.brexit.data.local.Local
import com.sanogueralorenzo.brexit.data.remote.ArticleDetailsApi
import io.reactivex.Observable
import javax.inject.Inject

class ArticleDetailsRepository
@Inject constructor
(private val articleDetailsApi: ArticleDetailsApi,
 private val data: Local) {

    private val ARTICLE = "ARTICLE_"

    fun getNetwork(articleUrl: String): Observable<String> = articleDetailsApi.getArticle(articleUrl, "body").map { it.response.content.fields.body }

    fun setData(articleUrl: String, body: String) = data.saveString(ARTICLE + articleUrl, body)

    fun getData(articleUrl: String): Observable<String> {
        return Observable.just(data.loadString(ARTICLE + articleUrl))
    }
}
