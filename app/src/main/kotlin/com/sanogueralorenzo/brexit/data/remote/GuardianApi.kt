package com.sanogueralorenzo.brexit.data.remote

import com.sanogueralorenzo.brexit.data.model.ArticleDetailsResponse
import com.sanogueralorenzo.brexit.data.model.ArticleListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Different interfaces for different Apis.
 *
 * It is useful to have them as separate interfaces to inject only the one required when creating
 * the repository. For example XApi is only used in XApiModule which will only be used by
 * provideXRepository
 */

//http://open-platform.theguardian.com/documentation/search
interface ArticleListApi {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    fun searchArticles(@Query("q") searchTerm: String): Observable<ArticleListResponse>
}

interface ArticleDetailsApi {
    @GET
    fun getArticle(@Url articleUrl: String, @Query("show-fields") fields: String): Observable<ArticleDetailsResponse>
}