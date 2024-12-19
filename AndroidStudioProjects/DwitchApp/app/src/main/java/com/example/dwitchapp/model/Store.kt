package com.example.dwitchapp.model

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.Date


@JsonClass(generateAdapter = true)
data class Store (
    val id: Long? = null,
    val documentId: String? = null,
    val name: String? = null,
    val isOpen: Boolean? = null,
    val city: String? = null,
    val zipCode: String? = null,
)