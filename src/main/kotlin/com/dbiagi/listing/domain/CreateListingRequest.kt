package com.dbiagi.listing.domain

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateListingRequest(
    @get:NotNull
    val title: String?,

    @get:NotNull
    val description: String?,

    val image: String?,

    val featured: Boolean = false,

    val price: BigDecimal = BigDecimal.ZERO,

    val ownerId: String,
)
