package com.dbiagi.listing.domain

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Listing(
    @Id
    val id: UUID? = null,

    val title: String?,

    val description: String?,

    val ownerId: String,

    val price: BigDecimal = BigDecimal.ZERO,

    val featured: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
