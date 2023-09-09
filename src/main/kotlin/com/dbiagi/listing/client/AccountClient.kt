package com.dbiagi.listing.client

import com.dbiagi.listing.domain.Account
import com.dbiagi.listing.domain.AccountSearchResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import reactor.core.publisher.Mono

@HttpExchange("/accounts")
interface AccountClient {
    @GetExchange("/{id}")
    fun getById(@PathVariable("id") id: String): Mono<Account>

    @GetExchange
    fun searchPaginated(@RequestParam("page") page: Int): Mono<AccountSearchResponse>
}
