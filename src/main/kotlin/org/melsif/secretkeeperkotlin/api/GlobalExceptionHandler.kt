package org.melsif.secretkeeperkotlin.api

import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler(val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    private val logger = LoggerFactory.getLogger(this::class.java)
    companion object {
        const val TRACE = "trace"
        const val VALIDATION_ERROR = "validation.error"
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleAllUncaughtException(exception: Exception, request: WebRequest): ResponseEntity<String> {
        logger.error("Unknown error occurred", exception)
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Unknown error occurred")
    }
}
