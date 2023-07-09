package com.dbiagi.listing.domain

import java.time.LocalDateTime
import java.util.*

data class Account(
    val id: UUID,

    val email: String,

    val name: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
