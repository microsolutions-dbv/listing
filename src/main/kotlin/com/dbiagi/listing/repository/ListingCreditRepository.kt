package com.dbiagi.listing.repository

import com.dbiagi.listing.model.ListingCredit
import org.springframework.data.r2dbc.repository.R2dbcRepository

interface ListingCreditRepository : R2dbcRepository<ListingCredit, Int>{
}
