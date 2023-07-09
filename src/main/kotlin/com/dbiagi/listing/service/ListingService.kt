package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.config.Exchanges
import com.dbiagi.listing.config.RoutingKeys
import com.dbiagi.listing.domain.Account
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.domain.Listing
import com.dbiagi.listing.domain.UpdateListingRequest
import com.dbiagi.listing.repository.ListingRepository
import mu.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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
        return Mono.just(request)
            .map { logger.info("listing create request request={}", request) }
            .flatMap { accountClient.getById(request.ownerId) }
            .map { account -> toListing(request, account) }
            .flatMap { listingRepository.save(it) }
            .doOnSuccess {
                rabbitMqService.sendMessage(Exchanges.LISTING_CREATED, RoutingKeys.LISTING_CREATED, it)
                logger.info("listing created listing={}", it)
            }
            .doOnError { ex ->
                logger.error("error creating listing request=$request", ex)
            }
    }

    fun delete(listing: Listing): Mono<Void> = listingRepository.delete(listing)

    fun toListing(request: CreateListingRequest, account: Account) =
        Listing(
            description = request.description ?: "",
            featured = request.featured,
            price = request.price,
            ownerId = account.id.toString(),
            title = request.title
        )

    fun getListing(id: String): Mono<Listing> = listingRepository.findById(UUID.fromString(id))

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
            .flatMap { listingRepository.save(it) }
            .doOnSuccess { rabbitMqService.sendMessage(Exchanges.LISTING_UPDATED, RoutingKeys.LISTING_UPDATED, it) }

    fun getListings(page: Pageable): Flux<Listing> {
        logger.info("get listings page=${page}")

        return listingRepository.findSorted(page)
    }

    fun getFeaturedListings(page: Pageable): Flux<Listing> {
        logger.info("get featured listings page=${page}")
        return listingRepository.findFeatured(page)
    }
}
