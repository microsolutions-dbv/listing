package com.dbiagi.listing.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig(
    private val amqpAdmin: AmqpAdmin
) {
    @Bean
    fun setupQueues() {
        declareFanoutExchange(Exchanges.LISTING_CREATED)
        declareDirectQueueAndDlq(Exchanges.LISTING_UPDATED, Queues.LISTING_UPDATED, RoutingKeys.LISTING_UPDATED)
    }

    private fun declareFanoutExchange(exchangeName: String) {
        val exchange = FanoutExchange(exchangeName)
        amqpAdmin.declareExchange(exchange)
    }

    private fun declareDirectQueueAndDlq(
        exchangeName: String,
        queueName: String,
        routingKey: String
    ) {
        val queue = QueueBuilder.durable(queueName)
            .deadLetterExchange(dlx(exchangeName))
            .deadLetterRoutingKey(routingKey)
            .build()

        val dlq = Queue(dlq(queueName))
        val exchange = DirectExchange(exchangeName)
        val dlx = DirectExchange(dlx(exchangeName))

        amqpAdmin.declareQueue(queue)
        amqpAdmin.declareQueue(dlq)
        amqpAdmin.declareExchange(exchange)
        amqpAdmin.declareExchange(dlx)
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey))
        amqpAdmin.declareBinding(BindingBuilder.bind(dlq).to(dlx).with(routingKey))
    }

    private fun dlq(queue: String): String = "dlq.$queue"

    private fun dlx(exchange: String): String = "dlx.$exchange"
}
