package com.dbiagi.listing.controller

import com.dbiagi.listing.ListingApplicationTest
import com.dbiagi.listing.fixture.CreateListingRequestFixture
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

class ListingControllerTestIT(webTestClient: WebTestClient) : ListingApplicationTest({

    test("given a random UUID should return 404 when searching for a listing") {
        val uuid = UUID.randomUUID().toString()

        webTestClient
            .get().uri("/listings/$uuid")
            .exchange()
            .expectStatus()
            .isNotFound
    }

    test("given a listing request should create a listing") {
        val request = CreateListingRequestFixture.getRequest()

        webTestClient
            .post().uri("/listings")
            .bodyValue(request)
            .exchange()
            .expectStatus()
            .isCreated
            .expectBody()
            .jsonPath("$.id").isNotEmpty
    }

    test("given a random listing id when updating should return 404") {
        val uuid = UUID.randomUUID().toString()

        webTestClient
            .patch().uri("/listings/$uuid")
            .bodyValue(CreateListingRequestFixture.getRequest())
            .exchange()
            .expectStatus()
            .isNotFound
    }
})
