package com.dbiagi.listing.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.paging")
data class PagingConfig(
    var maxItemsPerPage: Int = 10,
    var defaultPageSize: Int = 1,
    var defaultSortProperty: String = ""
)

@Configuration
@ConfigurationProperties(prefix = "app.services")
data class ServicesConfig(
    var connectTimeout: Long = 5, // time in seconds
    var timeout: Long = 10, // time in seconds
    var accountUrl: String = "",
    var oauthServerUrl: String = "",
)
