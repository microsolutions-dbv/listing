package com.dbiagi.listing.service

import com.dbiagi.listing.domain.CreateListingRequest
import com.dbiagi.listing.repository.ListingRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import reactor.core.publisher.Mono
import spock.lang.Specification

class ListingServiceTest extends Specification {
    private ListingRepository listingRepository
    private ListingService listingService
    private RabbitMessagingTemplate messagingTemplate
    private ObjectMapper objectMapper

    def setup() {
        listingRepository = Mock(ListingRepository)
        messagingTemplate= Mock(RabbitMessagingTemplate)
        objectMapper = new ObjectMapper()
        listingService = new ListingService(listingRepository, messagingTemplate, objectMapper)
    }

    def "given a create listing request should save"() {
        given:
            def request = new CreateListingRequest("", false, BigDecimal.ONE, null)
        when:
            listingService.create(request).block()
        then:
            1 * listingRepository.save(_) >> Mono.just(_)
            1 * messagingTemplate.convertAndSend("listing.created", "listing.created", _ as String)
    }

    def "given a create listing request should map to a listing object"() {
        given:
            def request = new CreateListingRequest("xxx", false, BigDecimal.ONE, null)
        when:
            def result = listingService.toListing(request)
        then:
            result.description == request.description
            result.price == request.price
            result.featured == request.featured
            result.image == request.image
    }
}
