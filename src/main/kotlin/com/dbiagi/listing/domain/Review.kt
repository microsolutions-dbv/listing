package com.dbiagi.listing.domain

import java.time.LocalDateTime
import java.util.*

data class Review (
    val id: UUID = UUID.randomUUID(),
    val headline: String?,
    val body: String,
    val rating: Float,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
