package com.dbiagi.listing.model

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Enumerated(EnumType.STRING)
    val type: ListingType = ListingType.NORMAL,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
