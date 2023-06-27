package com.dbiagi.listing.service

import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.fixture.CreateListingFixture
import com.dbiagi.listing.repository.ListingRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import java.math.BigDecimal

class ListingServiceTest {
    private val listingRepository: ListingRepository = mock()
    private val rabbitmqService: RabbitMqService = mock()

    private val listingService = ListingService(listingRepository, rabbitmqService)

    @Test
    fun `given a listing request when toListing is called then it should return a listing`() {
        // given
        val request: CreateListingRequest = CreateListingFixture.getListing()
        // when
        val result = listingService.toListing(request)
        // then
        assertSame(request.description, result.description)
    }
}
