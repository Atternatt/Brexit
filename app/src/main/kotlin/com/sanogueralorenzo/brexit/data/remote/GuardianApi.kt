package com.sanogueralorenzo.brexit.data.remote

import com.sanogueralorenzo.brexit.data.model.ArticleDetailsResponse
import com.sanogueralorenzo.brexit.data.model.ArticleListResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

//http://open-platform.theguardian.com/documentation/search
const val SHOW_FIELDS = "headline,thumbnail"
const val PAGE_SIZE = 25

interface ArticleListApi {
    @GET("search?show-fields=$SHOW_FIELDS&page-size=$PAGE_SIZE")
    fun searchArticles(@Query("page") page: Int, @Query("q") searchTerm: String): Observable<ArticleListResponse>
}

interface ArticleDetailsApi {
    @GET
    fun getArticle(@Url articleUrl: String, @Query("show-fields") fields: String): Observable<ArticleDetailsResponse>
}