package com.dbiagi.listing.service

import com.dbiagi.listing.Exchanges
import com.dbiagi.listing.RoutingKeys
import com.dbiagi.listing.document.Listing
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.repository.ListingRepository
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ListingService(private val listingRepository: ListingRepository,
                     private val messagingTemplate: RabbitMessagingTemplate,
                     private val objectMapper: ObjectMapper) {

    private val logger = KotlinLogging.logger {}

    fun create(request: CreateListingRequest): Mono<Listing> {
        return Mono.just(request)
            .map { logger.info("listing create request request={}", request) }
            .map { toListing(request) }
            .flatMap { listingRepository.save(it) }
            .doOnSuccess {
                notify(Exchanges.LISTING_CREATED, RoutingKeys.LISTING_CREATED, it)
                logger.info("listing created listing={}", it)
            }
            .doOnError { ex ->
                logger.error("error creating listing request=$request", ex)
            }
    }

    fun delete(listing: Listing): Mono<Unit> {
        return Mono.just(listing)
            .map { listingRepository.delete(listing) }
    }

    fun toListing(request: CreateListingRequest) =
            Listing(
                description = request.description,
                featured = request.featured,
                price = request.price,
                image = request.image,
                ownerId = null,
            )

    fun notify(exchange: String, routingKey: String, listing: Listing) {
        val payload = objectMapper.writeValueAsBytes(listing)

        messagingTemplate.convertAndSend(exchange, routingKey, payload)
    }
}
