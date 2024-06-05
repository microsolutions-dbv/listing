package com.dbiagi.listing.model

import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.*

data class ListingCredit(
    @Id
    @GeneratedValue
    val id: Int,

    val available: Boolean,

    val createdAt: LocalDateTime,

    val reservedAt: LocalDateTime?,

    val expiresAt: LocalDateTime?,

    val transactionId: UUID,

    @Enumerated
    val type: ListingType,

    @ManyToOne
    @JoinColumn(name = "balance_id")
    val listingAccountBalance: ListingAccountBalance,

    @OneToOne
    @JoinColumn(name = "listing_id")
    val listing: Listing?
)
