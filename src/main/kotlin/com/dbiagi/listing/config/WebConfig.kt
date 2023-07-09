package com.dbiagi.listing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver

@Configuration
class WebConfig {
    @Bean
    fun reactivePageableHandlerMethodArgumentResolver(pagingConfig: PagingConfig): HandlerMethodArgumentResolver {
        val resolver = ReactivePageableHandlerMethodArgumentResolver()
        resolver.setMaxPageSize(pagingConfig.maxItemsPerPage)
        return resolver
    }
}
