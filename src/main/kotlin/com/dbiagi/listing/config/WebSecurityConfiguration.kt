package com.dbiagi.listing.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebFluxSecurity
class WebSecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http.securityMatcher("/listings/**")
//            .authorizeRequests()
//            .mvcMatchers("/listings/**")
//            .access("hasAuthority('listing.read')")
//            .and()
//            .oauth2ResourceServer()
//            .jwt()

        return http.build()
    }
}
