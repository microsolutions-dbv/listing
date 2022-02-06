package com.dbiagi.listing.controller

import com.dbiagi.listing.document.Listing
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.service.ListingService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ListingController(
    val listingService: ListingService
) {
    @PostMapping("/")
    fun create(@Validated request: CreateListingRequest): Mono<Listing> {
        return listingService.create(request)
    }
}
