package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.domain.Account
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val accountClient: AccountClient
) {
    val logger = mu.KotlinLogging.logger {}
    fun getAccount(id: String): Mono<Account> = accountClient.getById(id)
        .map { account ->
            logger.info("account found for id=$id, account={}", account)
            account
        }
        .doOnError { error ->
            logger.error("error while getting account for id=$id, message=${error.message}", error)
        }
}
