package com.dbiagi.listing.listener

import com.dbiagi.listing.config.Queues
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ListingUpdated {
    val logger = KotlinLogging.logger {}

    @RabbitListener(queues = [Queues.LISTING_UPDATED])
    fun onListingUpdated(listing: String) {
        logger.info("listing updated payload=${listing}")
    }
}
