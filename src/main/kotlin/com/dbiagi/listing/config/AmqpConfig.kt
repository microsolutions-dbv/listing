package com.dbiagi.listing.config

import com.dbiagi.listing.service.rabbit.RabbitErrorHandler
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig(
    private val amqpAdmin: AmqpAdmin
) {
    enum class ExchangeType {
        FANOUT,
        DIRECT,
        TOPIC
    }

    @Bean
    fun setupQueues() {
        val listingCreatedExchange = declareExchange(Exchanges.LISTING_CREATED, ExchangeType.FANOUT)
        declareQueueAndDlq(listingCreatedExchange, Queues.LISTING_CREATED)

        val listingUpdatedExchange = declareExchange(Exchanges.LISTING_UPDATED, ExchangeType.FANOUT)
        declareQueueAndDlq(listingUpdatedExchange, Queues.LISTING_UPDATED)
    }

//    @Bean
//    fun rabbitListenerContainerFactory(
//        connectionFactory: ConnectionFactory,
//        configurer: SimpleRabbitListenerContainerFactoryConfigurer,
//        rabbitErrorHandler: RabbitErrorHandler
//    ): SimpleRabbitListenerContainerFactory {
//        val factory = SimpleRabbitListenerContainerFactory()
//        configurer.configure(factory, connectionFactory)
//
//        return factory
//    }

    private fun declareExchange(exchangeName: String, type: ExchangeType): Exchange {
        val exchange: Exchange = when (type) {
            ExchangeType.FANOUT -> FanoutExchange(exchangeName)
            ExchangeType.DIRECT -> DirectExchange(exchangeName)
            ExchangeType.TOPIC -> TopicExchange(exchangeName)
        }

        amqpAdmin.declareExchange(exchange)

        return exchange
    }

    private fun declareQueueAndDlq(exchange: Exchange, queueName: String, routingKey: String? = null): Queue {
        val queue = QueueBuilder.durable(queueName)
            .deadLetterExchange(dlx(queueName))
            .deadLetterRoutingKey(dlq(queueName))
            .build()

        val dlq = Queue(dlq(queueName))
        val dlx = DirectExchange(dlx(queueName))

        amqpAdmin.declareQueue(queue)
        amqpAdmin.declareQueue(dlq)
        amqpAdmin.declareExchange(dlx)
        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey ?: "").noargs())
        amqpAdmin.declareBinding(BindingBuilder.bind(dlq).to(dlx).withQueueName())

        return queue
    }

    private fun dlq(queue: String): String = "dlq.$queue"

    private fun dlx(exchange: String): String = "dlx.$exchange"
}
