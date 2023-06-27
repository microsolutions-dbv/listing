package com.dbiagi.listing.fixture

import com.dbiagi.listing.domain.CreateListingRequest
import java.math.BigDecimal

object CreateListingFixture {
    fun getListing(): CreateListingRequest = CreateListingRequest(
        description = "Listing description",
        featured = true,
        price = BigDecimal.TEN,
        image = null
    )
}
