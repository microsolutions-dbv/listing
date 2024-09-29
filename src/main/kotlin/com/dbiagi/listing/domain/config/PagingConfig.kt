package com.dbiagi.listing.domain.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.paging")
data class PagingConfig(
    var maxItemsPerPage: Int = 10,
    var defaultPageSize: Int = 1,
    var defaultSortProperty: String = ""
)
