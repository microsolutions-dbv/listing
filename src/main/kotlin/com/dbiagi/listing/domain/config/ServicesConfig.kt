package com.dbiagi.listing.domain.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.services")
data class ServicesConfig(
    var connectTimeout: Long = 5, // time in seconds
    var timeout: Long = 10, // time in seconds
    var accountUrl: String = "",
    var oauthServerUrl: String = "",
)
