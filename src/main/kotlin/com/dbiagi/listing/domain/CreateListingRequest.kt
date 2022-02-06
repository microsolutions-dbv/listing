package com.dbiagi.listing.domain

import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class CreateListingRequest(
    @get:NotNull
    val description: String,
    val featured: Boolean,
    val price: BigDecimal?,
    val image: String?
)
