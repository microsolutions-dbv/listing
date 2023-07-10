package com.dbiagi.listing.fixture

import com.dbiagi.listing.domain.CreateListingRequest
import java.math.BigDecimal

object CreateListingRequestFixture {
    fun getListing(): CreateListingRequest = CreateListingRequest(
        description = "Listing description",
        featured = true,
        price = BigDecimal.TEN,
        image = null,
        title = "Listing title",
        ownerId = "8f6f6ff0-beb5-4f31-8b13-858986b3b344"
    )
}
