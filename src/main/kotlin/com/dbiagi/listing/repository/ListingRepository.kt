package com.dbiagi.listing.repository

import com.dbiagi.listing.document.Listing
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface ListingRepository : ReactiveCrudRepository<Listing, ObjectId> {
}
