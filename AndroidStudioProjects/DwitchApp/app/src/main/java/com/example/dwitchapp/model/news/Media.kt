package com.example.dwitchapp.model.news

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class Media (
    val id: Long,
    val name: String,
    val url: String,
)