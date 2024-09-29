package com.dbiagi.listing.config

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.domain.config.ServicesConfig
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class ClientConfig {

    @Bean
    fun accountClient(apiWebClient: WebClient): AccountClient = createInterfaceClient(apiWebClient, AccountClient::class.java)

    @Bean
    fun apiWebClient(servicesConfig: ServicesConfig, objectMapper: ObjectMapper): WebClient {
        val httpClient = httpClient(
            servicesConfig.connectTimeout,
            servicesConfig.timeout,
        )
        val size = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder().codecs {
            it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            it.defaultCodecs().maxInMemorySize(size)
        }.build()

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(strategies)
            .defaultHeaders {
                it.contentType = MediaType.APPLICATION_JSON
                it.accept = listOf(MediaType.APPLICATION_JSON)
            }
            .baseUrl(servicesConfig.accountUrl)
            .build()
    }

    private fun httpClient(connectTimeout: Long, timeout: Long): HttpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (connectTimeout.toInt() * 1000))
        .responseTimeout(Duration.ofSeconds(timeout))
        .doOnConnected { conn ->
            conn.addHandlerLast(ReadTimeoutHandler(timeout, TimeUnit.SECONDS))
                .addHandlerLast(WriteTimeoutHandler(timeout, TimeUnit.SECONDS))
        }


    private fun <T> createInterfaceClient(apiWebClient: WebClient, clazz: Class<T>): T = HttpServiceProxyFactory
        .builder(WebClientAdapter.forClient(apiWebClient))
        .build()
        .createClient(clazz)
}
