package com.dbiagi.listing.repository

import com.dbiagi.listing.model.ListingAccountBalance
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface ListingAccountBalanceRepository : R2dbcRepository<ListingAccountBalance, Int>{
}
