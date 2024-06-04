package com.dbiagi.listing.repository

import com.dbiagi.listing.model.Listing
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import java.util.*

interface ListingRepository : R2dbcRepository<Listing, UUID> {
    @Query("SELECT l.* FROM listing l ORDER BY l.featured DESC, l.price ASC")
    fun findSorted(pageable: Pageable): Flux<Listing>

    @Query("SELECT * FROM listing l WHERE l.featured ORDER BY l.price ASC")
    fun findFeatured(pageable: Pageable): Flux<Listing>

    fun findByOwnerId(ownerId: String, pageable: Pageable): Flux<Listing>
}
