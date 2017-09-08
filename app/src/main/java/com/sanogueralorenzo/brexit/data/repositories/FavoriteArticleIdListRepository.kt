package com.sanogueralorenzo.brexit.data.repositories

import com.google.gson.reflect.TypeToken
import com.sanogueralorenzo.brexit.data.local.Local
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class FavoriteArticleIdListRepository
@Inject constructor
(private val data: Local) {

    private val FAVORITE_ARTICLE_URL_LIST = "FAVORITE_ARTICLE_URL_LIST"

    fun setData(favoriteArticleList: List<String>) = data.save(FAVORITE_ARTICLE_URL_LIST, favoriteArticleList)

    fun getData(): Observable<List<String>> {
        return Observable.just(data.load(FAVORITE_ARTICLE_URL_LIST, object : TypeToken<List<String>>() {}.type) ?: ArrayList())
    }
}
