package com.dbiagi.listing.client

import com.dbiagi.listing.domain.Account
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Mono

@HttpExchange("/accounts")
interface AccountClient {
    @GetExchange("/{id}")
    fun getById(id: String): Mono<Account>
}