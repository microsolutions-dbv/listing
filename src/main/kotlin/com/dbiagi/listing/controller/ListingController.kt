package com.dbiagi.listing.controller

import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.model.Listing
import com.dbiagi.listing.domain.UpdateListingRequest
import com.dbiagi.listing.domain.exception.NotFoundException
import com.dbiagi.listing.service.ListingService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.UUID

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
    fun getListing(@PathVariable("id") id: UUID): Mono<Listing> {
        logger.info("getting listing with id $id")
        return listingService.getListing(id).switchIfEmpty { Mono.error(NotFoundException())}
    }

    @GetMapping
    fun list(page: Pageable): Flux<Listing> = listingService.getListings(page)

    @GetMapping("/featured")
    fun featured(page: Pageable): Flux<Listing> = listingService.getFeaturedListings(page)

    @GetMapping("/{id}/owner")
    fun findByOwnerId(page: Pageable, @PathVariable("id") id: String): Flux<Listing> =
        listingService.findByOwnerId(page, id)
}
