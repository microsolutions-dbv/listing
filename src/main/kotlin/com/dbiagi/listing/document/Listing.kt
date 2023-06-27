package com.dbiagi.listing.document

import com.dbiagi.listing.domain.Review
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Listing(
    val description: String,

    val featured: Boolean,

    val price: BigDecimal?,

    val identifier: UUID = UUID.randomUUID(),

    val image: String?,

    val ownerId: UUID?,

    val reviews: List<Review> = listOf(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime? = null
)
