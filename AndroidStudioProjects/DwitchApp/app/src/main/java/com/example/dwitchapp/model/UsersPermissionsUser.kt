package com.example.dwitchapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.Date


@JsonClass(generateAdapter = true)
data class UsersPermissionsUser (
    val id: Long? = null,
    val documentID: String? = null,
    val username: String? = null,
    val email: String? = null,
    val provider: String? = null,
    val confirmed: Boolean? = null,
    val blocked: Boolean? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val publishedAt: LocalDateTime? = null
)
