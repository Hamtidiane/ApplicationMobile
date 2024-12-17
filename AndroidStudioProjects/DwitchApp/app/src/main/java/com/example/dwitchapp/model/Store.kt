package com.example.dwitchapp.model

import java.time.LocalDateTime
import java.util.Date

data class Store (
    val id: Long? = null,
    val documentID: String? = null,
    val name: String? = null,
    val isOpen: Boolean? = null,
    val city: String? = null,
    val zipCode: String? = null,
    val createdAt: Date ?= null,
    val updatedAt: Date? = null,
    val publishedAt: Date? = null
)