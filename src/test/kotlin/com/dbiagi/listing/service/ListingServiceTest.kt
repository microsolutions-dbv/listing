package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.model.Listing
import com.dbiagi.listing.fixture.CreateListingRequestFixture
import com.dbiagi.listing.fixture.ListingFixtures
import com.dbiagi.listing.repository.ListingRepository
import io.kotest.core.spec.style.FunSpec
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class ListingServiceTest : FunSpec({
    val listingRepository: ListingRepository = mock()
    val rabbitmqService: RabbitMqService = mock()
    val accountClient: AccountClient = mock()

    val listingService = ListingService(listingRepository, rabbitmqService, accountClient)

    test("given a listing request when toListing is called then it should return a listing") {
        val request: CreateListingRequest = CreateListingRequestFixture.getRequest()
        val listing = ListingFixtures.getListing()

        whenever(listingRepository.save(any())).thenReturn(Mono.just(listing))

        StepVerifier.create(listingService.create(request))
            .expectNextMatches { createdListing: Listing ->
                createdListing.description == request.description
            }
            .verifyComplete()
    }
})
