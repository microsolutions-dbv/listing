package com.dbiagi.listing.service

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.messaging.MessagingException
import org.springframework.stereotype.Service

@Service
class RabbitMqService(
    private val rabbitMessagingTemplate: RabbitMessagingTemplate,
    private val objectMapper: ObjectMapper
) {
    private val logger = KotlinLogging.logger {}

    fun sendMessage(exchange: String, routingKey: String?, payload: Any) {
        try {
            val jsonPayload = objectMapper.writeValueAsString(payload)
            rabbitMessagingTemplate.convertAndSend(exchange, routingKey ?: "", jsonPayload, defaultHeaders())
        } catch (e: MessagingException) {
            logger.error("Error sending message to exchange $exchange with routingKey $routingKey. Cause: ${e.message}", e)
        } catch (e: Exception) {
            logger.error("Unknown error sending message to exchange $exchange with routingKey $routingKey.  Cause: ${e.message}", e)
        }
    }

    private fun defaultHeaders(): Map<String, String> = mapOf(
        HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE
    )
}
