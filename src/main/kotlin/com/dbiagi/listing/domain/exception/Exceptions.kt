package com.dbiagi.listing.domain.exception

class NotFoundException(errors: List<AppError> = emptyList()) : BaseException(errors)
class BadRequestException(errors: List<AppError> = emptyList()) : BaseException(errors)
class UnauthorizedException(errors: List<AppError> = emptyList()) : BaseException(errors)
class UnprocessableException(errors: List<AppError> = emptyList()) : BaseException(errors)

fun notFound(error: AppError): NotFoundException = NotFoundException(listOf(error))

fun badRequest(errorCode: String, args: Map<String, String> = emptyMap()): BadRequestException =
    BadRequestException(listOf(AppError(errorCode, args)))

fun internalError(): BaseException = BaseException(emptyList())

fun unprocessableEntity(errorCode: String, args: Map<String, String> = emptyMap()): UnprocessableException =
    UnprocessableException(listOf(AppError(errorCode, args)))
