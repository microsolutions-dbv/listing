package com.dbiagi.listing.domain

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateListingRequest(
    @get:NotNull
    val description: String,
    val featured: Boolean,
    val price: BigDecimal?,
    val image: String?
)
