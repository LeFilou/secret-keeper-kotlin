package org.melsif.secretkeeperkotlin.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.context.MessageSource
import org.springframework.context.support.StaticMessageSource
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.method.HandlerMethod
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod


abstract class ControllerTest {
    @BeforeEach
    fun setUp() {
        val messageSource = StaticMessageSource().apply {
            setUseCodeAsDefaultMessage(true)
        }
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(getTestedController())
            .setHandlerExceptionResolvers(restErrorHandler(messageSource))
            .setMessageConverters(jacksonConverter())
            .build()
        RestAssuredMockMvc.mockMvc(mockMvc)
    }

    abstract fun getTestedController(): Any

    private fun restErrorHandler(messageSource: MessageSource): ExceptionHandlerExceptionResolver {
        val exceptionResolver: ExceptionHandlerExceptionResolver = object : ExceptionHandlerExceptionResolver() {
            override fun getExceptionHandlerMethod(
                handlerMethod: HandlerMethod?,
                exception: Exception
            ): ServletInvocableHandlerMethod? {

                val method = ExceptionHandlerMethodResolver(GlobalExceptionHandler::class.java)
                    .resolveMethod(exception)
                return if (method != null) {
                    ServletInvocableHandlerMethod(GlobalExceptionHandler(messageSource), method)
                } else super.getExceptionHandlerMethod(handlerMethod, exception)
            }
        }
        exceptionResolver.messageConverters = listOf(jacksonConverter())
        exceptionResolver.afterPropertiesSet()
        return exceptionResolver
    }

    private fun jacksonConverter(): MappingJackson2HttpMessageConverter {
        val objectMapper = ObjectMapper()
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.registerModule(JavaTimeModule())
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = objectMapper
        return converter
    }
}
