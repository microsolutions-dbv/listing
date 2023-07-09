package com.dbiagi.listing.controller

import com.dbiagi.listing.ListingApplicationTest
import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.fixture.CreateListingRequestFixture
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.reactive.function.BodyInserters

class ListingControllerTestIT : ListingApplicationTest() {
    @Test
    fun `given a listing request should create a new listing`() {
        // given
        val request = CreateListingRequestFixture.getListing()

        // when
        webTestClient.post()
            .uri("/listings")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isCreated()
    }
}
