package com.dbiagi.listing.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/listings")
class ImportListingController {
    @PostMapping("/import")
    fun importListings() {

    }
}
