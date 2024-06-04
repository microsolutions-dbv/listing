package com.dbiagi.listing.listener

import com.dbiagi.listing.config.Queues
import com.dbiagi.listing.domain.exception.ListingCreationException
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ListingCreated {
    val logger = KotlinLogging.logger {}

    @RabbitListener(queues = [Queues.LISTING_CREATED])
    fun onListingCreated(listing: String) {
        logger.info("listing created payload=${listing}")

        throw ListingCreationException()
    }
}
