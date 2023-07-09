package com.dbiagi.listing.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.paging")
class PagingConfig {
    var maxItemsPerPage: Int = 10
}

@Configuration
@ConfigurationProperties(prefix = "app.services")
class ServicesConfig(
    var connectTimeout: Long = 5, // time in seconds
    var timeout: Long = 10, // time in seconds
    var baseUrl: String = ""
)
