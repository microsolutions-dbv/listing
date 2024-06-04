package com.dbiagi.listing.fixture

import com.dbiagi.listing.model.Listing
import java.math.BigDecimal

object ListingFixtures {
    fun getListing() = Listing(
        description = "Listing description",
        featured = true,
        price = BigDecimal.TEN,
        title = "Listing title",
        ownerId = "8f6f6ff0-beb5-4f31-8b13-858986b3b344"
    )
}
