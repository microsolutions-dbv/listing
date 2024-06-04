package com.dbiagi.listing.service

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RabbitMqService(
    private val rabbitMessagingTemplate: RabbitMessagingTemplate,
    private val objectMapper: ObjectMapper
) {
    private val logger = KotlinLogging.logger {}

    companion object {
        const val CONTENT_TYPE = "application/json"
    }

    fun publish(exchange: String, routingKey: String?, payload: Any) = Mono.defer {
        val jsonPayload = objectMapper.writeValueAsString(payload)
        enqueue(exchange, routingKey, jsonPayload)
    }

    private fun enqueue(exchange: String, routingKey: String?, payload: String): Mono<Void> {
        try {
            rabbitMessagingTemplate.convertAndSend(exchange, routingKey ?: "", payload, defaultHeaders())

            return Mono.empty()
        } catch (e: Exception) {
            logger.error("Unknown error sending message to exchange $exchange with routingKey $routingKey, payload $payload.  Cause: ${e.message}", e)

            return Mono.error(RuntimeException("Error sending message to exchange $exchange with routingKey $routingKey"))
        }
    }

    private fun defaultHeaders(): Map<String, String> = mapOf(
        "Content-Type" to CONTENT_TYPE
    )
}
