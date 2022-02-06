package com.dbiagi.listing.fixture

import com.dbiagi.listing.document.Listing

import java.time.LocalDateTime

class ListingFixture {
    static Listing getListing() {
        new Listing(
                "xxx",
                false,
                BigDecimal.TEN,
                UUID.randomUUID(),
                null,
                UUID.randomUUID(),
                [],
                LocalDateTime.now()
        )
    }
}
