package com.dbiagi.listing.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.*

data class ListingAccountBalance(
    @Id
    @GeneratedValue
    val id: Int?,

    @ManyToOne
    val credits: List<ListingCredit> = emptyList(),

    val accountId: UUID,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
