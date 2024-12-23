package com.example.dwitchapp.model.news

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class News (

    val id: Long? = null,
    val documentId: String? = null,
    val title: String? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val publishedAt: String? = null,
    val medias: List<Media>)