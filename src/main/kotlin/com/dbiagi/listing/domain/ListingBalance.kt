package com.dbiagi.listing.domain

import com.dbiagi.listing.model.ListingType

data class ListingBalance(
    val account: Account,
    val items: ListingBalanceItem
)

data class ListingBalanceItem(
    val total: Int,
    val available: Int,
    val type: ListingType
)
