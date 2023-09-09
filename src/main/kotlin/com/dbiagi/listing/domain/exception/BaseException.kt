package com.dbiagi.listing.domain.exception

open class BaseException(
    val errors: List<AppError> = emptyList(),
    override val cause: Throwable? = null
) : RuntimeException(cause)
