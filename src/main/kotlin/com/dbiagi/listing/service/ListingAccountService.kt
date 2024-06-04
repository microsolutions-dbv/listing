package com.dbiagi.listing.service

import com.dbiagi.listing.client.AccountClient
import com.dbiagi.listing.domain.ListingBalance
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class ListingAccountService(
    private val accountClient: AccountClient
) {
    fun getListingBalance(accountId: UUID): Mono<ListingBalance> = accountClient.getListingBalance(accountId.toString())
}
