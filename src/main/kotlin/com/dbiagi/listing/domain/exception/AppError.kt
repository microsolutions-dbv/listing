package com.dbiagi.listing.domain.exception

data class AppError(
    val code: String,
    val arguments: Map<String, String>
)
