package com.sanogueralorenzo.brexit.data.model

data class ArticleDetailsResponse(val response: DetailsResponse)

data class DetailsResponse(val content: Content)

data class Content(val fields: DetailsFields)

data class DetailsFields(val body: String)
