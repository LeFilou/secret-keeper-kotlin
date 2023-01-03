package org.melsif.secretkeeperkotlin.api

import io.mockk.junit5.MockKExtension
import io.restassured.module.mockmvc.kotlin.extensions.Given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CredentialsControllerTest : ControllerTest() {

    override fun getTestedController(): Any {
        return CredentialsController()
    }

    @Test
    fun `returns not implemented`() {
        Given {
            header("application-content", "json")
        } When {
            get("/credentials")
        } Then {
            statusCode(501)
        }
    }
}
