package com.dbiagi.listing.controller

import com.dbiagi.listing.domain.exception.BadRequestException
import com.dbiagi.listing.domain.exception.BaseException
import com.dbiagi.listing.domain.exception.ErrorResponse
import com.dbiagi.listing.domain.exception.NotFoundException
import com.dbiagi.listing.domain.exception.UnprocessableException
import com.dbiagi.listing.domain.exception.internalError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {
    val logger = mu.KotlinLogging.logger {}

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unhandled exception!", exception)
        internalError().errors
        return ResponseEntity
            .internalServerError()
            .body(ErrorResponse(errors = emptyList()))
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(toStatus(exception))
            .body(ErrorResponse(errors = exception.errors))

    private fun toStatus(exception: BaseException): HttpStatus = when (exception) {
        is BadRequestException -> HttpStatus.BAD_REQUEST
        is NotFoundException -> HttpStatus.NOT_FOUND
        is UnprocessableException -> HttpStatus.UNPROCESSABLE_ENTITY
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}
