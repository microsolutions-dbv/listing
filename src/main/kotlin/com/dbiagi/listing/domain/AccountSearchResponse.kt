package com.dbiagi.listing.domain

data class AccountSearchResponse (
    val accounts: List<Account>,
    val page: AccountSearchPage
) {
    fun hasNextPage(): Boolean = page.next > 0
}

data class AccountSearchPage(
    val next: Int
)
