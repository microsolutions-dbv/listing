package com.dbiagi.listing.domain

import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.*

data class Review (
    @Id
    val id: UUID? = null,
    val title: String?,
    val description: String,
    val rating: Float,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
