package com.dbiagi.listing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler

@EnableWebFluxSecurity
@Configuration
class WebSecurityConfiguration {
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity, logoutHandler: DelegatingServerLogoutHandler): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers("/listings/**").permitAll()
                    .anyExchange().authenticated()
            }
            .build()
}
