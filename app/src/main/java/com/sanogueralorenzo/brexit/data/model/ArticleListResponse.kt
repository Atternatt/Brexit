package com.sanogueralorenzo.brexit.data.model

import java.util.*

data class ArticleListResponse(val response: ListResponse)

data class ListResponse(val results: List<Result>)

data class Result(val webPublicationDate: Date?, val apiUrl: String?, val fields: ListFields?)

data class ListFields(val headline: String?, val thumbnail: String?)
