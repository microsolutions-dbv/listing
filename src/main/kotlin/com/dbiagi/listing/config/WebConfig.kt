package com.dbiagi.listing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver

@Configuration
class WebConfig {
    @Bean
    fun reactivePageableHandlerMethodArgumentResolver(pagingConfig: PagingConfig): HandlerMethodArgumentResolver {
        val resolver = ReactivePageableHandlerMethodArgumentResolver()
        resolver.setMaxPageSize(pagingConfig.maxItemsPerPage)
        resolver.setFallbackPageable(defaultPage(pagingConfig))
        return resolver
    }

    private fun defaultPage(pagingConfig: PagingConfig): PageRequest =
        PageRequest.of(0, pagingConfig.defaultPageSize, Sort.Direction.ASC, pagingConfig.defaultSortProperty)
}
