package com.dbiagi.listing.config

import com.dbiagi.listing.domain.config.SecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
@Configuration
class WebSecurityConfiguration(
    private val securityConfig: SecurityConfig
) {
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .authorizeExchange { exchanges ->
                securityConfig.publicPaths.forEach { path ->
                    exchanges
                        .pathMatchers("*").authenticated()
                        .pathMatchers(path).permitAll()
                }
            }
            .build()
}
