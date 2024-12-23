package com.example.dwitchapp.model.news

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val data: List<News>

)
