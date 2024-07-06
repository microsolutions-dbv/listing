package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.config.Exchanges
import com.dbiagi.listing.config.RoutingKeys
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.domain.UpdateListingRequest
import com.dbiagi.listing.domain.exception.NotFoundException
import com.dbiagi.listing.model.Listing
import com.dbiagi.listing.repository.ListingRepository
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime
import java.util.*

@Service
class ListingService(
    private val listingRepository: ListingRepository,
    private val rabbitMqService: RabbitMqService,
    private val accountClient: AccountClient
) {

    private val logger = KotlinLogging.logger {}

    fun create(request: CreateListingRequest): Mono<Listing> {
        // check if the owner exists and is valid, if not return an error
        // check if owner has listings available to create, if not return an error
        // after create the listing, update owner listings count
        logger.info("listing create request request={}", request)
        val listing = toListing(request)

        return listingRepository.save(listing)
            .map {
                logger.info("listing created listing={}", it)
                rabbitMqService.publish(Exchanges.LISTING_CREATED, null, it)
                it
            }
            .doOnError { ex -> logger.error("error creating listing request=$request", ex) }
    }

    fun deactivate(id: String): Mono<Void> = listingRepository.deactivate(id)

    fun toListing(request: CreateListingRequest) =
        Listing(
            description = request.description ?: "",
            featured = request.featured,
            price = request.price,
            ownerId = request.ownerId,
            title = request.title
        )

    fun getListing(id: UUID): Mono<Listing> = listingRepository.findById(id)

    fun update(id: String, request: UpdateListingRequest): Mono<Listing> =
        listingRepository.findById(UUID.fromString(id))
            .map { listing ->
                logger.info("updating listing with id=${id} with request=${request}")
                listing.copy(
                    title = request.title ?: listing.title,
                    description = request.description ?: listing.description,
                    updatedAt = LocalDateTime.now()
                )
            }
            .switchIfEmpty { Mono.error(NotFoundException()) }
            .flatMap { listingRepository.save(it) }
            .doOnSuccess { rabbitMqService.publish(Exchanges.LISTING_UPDATED, RoutingKeys.LISTING_UPDATED, it) }
            .doOnError { ex -> logger.error("error updating listing id=${id}, request=${request}, message=${ex.message}", ex) }

    fun getListings(page: Pageable): Flux<Listing> {
        logger.info("get listings page=${page}")
        return listingRepository.findAll(page.sort)
    }

    fun getFeaturedListings(page: Pageable): Flux<Listing> {
        logger.info("get featured listings page=${page}")
        return listingRepository.findFeatured(page)
    }

    fun findByOwnerId(page: Pageable, id: String): Flux<Listing> {
        logger.info("get listings page=${page} for owner id=${id}")
        return listingRepository.findByOwnerId(id, page)
    }
}
