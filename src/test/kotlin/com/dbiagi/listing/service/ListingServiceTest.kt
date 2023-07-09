package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.fixture.CreateListingRequestFixture
import com.dbiagi.listing.repository.ListingRepository
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class ListingServiceTest {
    private val listingRepository: ListingRepository = mock()
    private val rabbitmqService: RabbitMqService = mock()
    private val accountClient: AccountClient = mock()

    private val listingService = ListingService(listingRepository, rabbitmqService, accountClient)

    @Test
    fun `given a listing request when toListing is called then it should return a listing`() {
        // given
        val request: CreateListingRequest = CreateListingRequestFixture.getListing()
        // when

        // then
        assertSame(request.description, "")
    }
}
