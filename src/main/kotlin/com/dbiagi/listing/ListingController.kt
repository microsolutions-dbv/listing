package com.dbiagi.listing

import com.dbiagi.listing.repository.ListingRepository
import org.springframework.web.bind.annotation.RestController
import javax.validation.Validator

@RestController
class ListingController(
    private val listingRepository: ListingRepository,
    private val validator: Validator,
)
