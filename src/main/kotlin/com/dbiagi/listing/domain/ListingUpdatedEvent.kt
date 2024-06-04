package com.dbiagi.listing.domain

import com.dbiagi.listing.model.Listing
import java.time.LocalDateTime

data class ListingUpdatedEvent(
    val before: Listing,
    val request: UpdateListingRequest,
    val changedAt: LocalDateTime = LocalDateTime.now()
)
