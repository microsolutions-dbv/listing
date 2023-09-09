package com.dbiagi.listing.controller

import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.domain.Listing
import com.dbiagi.listing.domain.UpdateListingRequest
import com.dbiagi.listing.domain.exception.AppError
import com.dbiagi.listing.domain.exception.badRequest
import com.dbiagi.listing.service.ListingService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/listings")
class ListingController(
    val listingService: ListingService
) {
    private val logger = mu.KotlinLogging.logger {}

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun create(@Validated @RequestBody request: CreateListingRequest): Mono<Listing> {
        return listingService.create(request)
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: String, @Validated @RequestBody request: UpdateListingRequest): Mono<Listing> {
        return listingService.update(id, request)
    }

    @GetMapping("/{id}")
    fun getListing(@PathVariable("id") id: String): Mono<Listing> {
        logger.info("getting listing with id $id")
        return listingService.getListing(id)
    }

    @GetMapping
    fun list(page: Pageable): Flux<Listing> = listingService.getListings(page)

    @GetMapping("/featured")
    fun featured(page: Pageable): Flux<Listing> = listingService.getFeaturedListings(page)

    @GetMapping("/owner/{id}")
    fun findByOwnerId(page: Pageable, @PathVariable("id") id: String): Flux<Listing> =
        listingService.findByOwnerId(page, id)

    @GetMapping("/error")
    fun error(): Mono<Listing> = Mono.error(badRequest("LISTING_NOT_FOUND", mapOf("id" to "123")))
}
