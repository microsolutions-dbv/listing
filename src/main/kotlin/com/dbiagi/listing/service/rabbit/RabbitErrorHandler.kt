package com.dbiagi.listing.service.rabbit

import mu.KotlinLogging
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException
import org.springframework.amqp.support.converter.MessageConversionException
import org.springframework.stereotype.Service
import org.springframework.util.ErrorHandler

@Service
class RabbitErrorHandler : ErrorHandler {
    val logger = KotlinLogging.logger {}

    override fun handleError(t: Throwable) {
        if(t !is ListenerExecutionFailedException) {
            logger.error("Error handling message")
            throw AmqpRejectAndDontRequeueException(t)
        }

        when (t) {
            is MessageConversionException -> {
                logger.error("Error converting message ")
                throw AmqpRejectAndDontRequeueException(t)
            }

            is IllegalArgumentException -> {
                println("Handling illegal argument exception")
            }

            else -> {
                println("Handling unknown exception")
            }
        }
    }
}
