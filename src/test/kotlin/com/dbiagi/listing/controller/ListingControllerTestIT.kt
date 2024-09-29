package com.dbiagi.listing.controller

import com.dbiagi.listing.ListingApplicationTest
import com.dbiagi.listing.fixture.CreateListingRequestFixture
import com.dbiagi.listing.fixture.ListingFixtures
import com.dbiagi.listing.repository.ListingRepository
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

class ListingControllerTestIT(webTestClient: WebTestClient, listingRepository: ListingRepository) :
    ListingApplicationTest({

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

        test("given an existing listing should return 200 when searching for a listing") {
            val listing = listingRepository.save(ListingFixtures.getListing()).block()

            webTestClient
                .get().uri("/listings/${listing!!.id}")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(listing.id.toString())
                .jsonPath("$.title").isEqualTo(listing.title.toString())
        }

        test("given an existing listing should update when patching") {
            val listing = listingRepository.save(ListingFixtures.getListing()).block()

            val request = CreateListingRequestFixture.getRequest()

            webTestClient
                .patch().uri("/listings/${listing!!.id}")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(listing.id.toString())
                .jsonPath("$.title").isEqualTo(request.title.toString())
        }
    })
