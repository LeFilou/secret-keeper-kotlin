package org.melsif.secretkeeperkotlin.api

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.restassured.module.mockmvc.kotlin.extensions.Given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GlobalExceptionHandlerTest : ControllerTest() {

    private lateinit var dummyService: DummyService

    override fun getTestedController(): Any {
        dummyService = mockk()
        return DummyController(dummyService)
    }

    @Test
    fun `returns http 200 if everything is good`() {
        every { dummyService.dummyFunction() } returns "dummy response"
        Given {
            header("content-type", "application/json")
            param("dummyParameter", "dummyValue")
        } When {
            get("/dummy")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `returns http 500 if something went wrong`() {
        every { dummyService.dummyFunction() } throws IllegalStateException("Something went wrong")
        Given {
            header("content-type", "application/json")
            param("dummyParameter", "dummyValue")
        } When {
            get("/dummy")
        } Then {
            statusCode(500)
        }
    }

    @Test
    fun `returns http bad request on missing parameter`() {
        every { dummyService.dummyFunction() } throws IllegalStateException("Something went wrong")
        Given {
            header("content-type", "application/json")
        } When {
            get("/dummy")
        } Then {
            statusCode(400)
        }
    }
}
