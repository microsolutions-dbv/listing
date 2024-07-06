package com.dbiagi.listing.controller

import com.dbiagi.listing.domain.exception.BadRequestException
import com.dbiagi.listing.domain.exception.BaseException
import com.dbiagi.listing.domain.exception.ErrorResponse
import com.dbiagi.listing.domain.exception.NotFoundException
import com.dbiagi.listing.domain.exception.UnprocessableException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {
    val logger = mu.KotlinLogging.logger {}

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Void> = handleUnknowException(exception)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException) = when (exception) {
        is BadRequestException -> handleBadRequestException(exception)
        is NotFoundException -> handleNotFoundException()
        is UnprocessableException -> handleBadRequestException(exception)
        else -> handleUnknowException(exception)
    }

    private fun handleBadRequestException(exception: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(errors = exception.errors))
    }

    private fun handleNotFoundException(): ResponseEntity<Void> {
        return ResponseEntity
            .notFound()
            .build()
    }

    private fun handleBadRequestException(exception: UnprocessableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .unprocessableEntity()
            .body(ErrorResponse(errors = exception.errors))
    }

    private fun handleUnknowException(exception: Exception): ResponseEntity<Void> {
        logger.error("Unhandled exception!", exception)
        return ResponseEntity
            .internalServerError()
            .build()
    }
}
