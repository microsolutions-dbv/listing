package com.dbiagi.listing.fixture

import com.dbiagi.listing.domain.CreateListingRequest
import java.math.BigDecimal
import java.util.UUID

object CreateListingRequestFixture {
    fun getRequest(): CreateListingRequest = CreateListingRequest(
        description = "Listing description",
        featured = true,
        price = BigDecimal.TEN,
        image = null,
        title = "Listing title",
        ownerId = UUID.randomUUID().toString()
    )
}
