package org.melsif.secretkeeperkotlin.api

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.restassured.module.mockmvc.kotlin.extensions.Given
import io.restassured.module.mockmvc.kotlin.extensions.Then
import io.restassured.module.mockmvc.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.melsif.secretkeeperkotlin.credentials.CredentialService
import org.melsif.secretkeeperkotlin.util.Generator.Companion.generateCredentials

@ExtendWith(MockKExtension::class)
class CredentialsControllerTest : ControllerTest() {

    private lateinit var credentialService: CredentialService
    override fun getTestedController(): Any {
        credentialService = mockk()
        return CredentialsController(credentialService)
    }

    @Nested
    inner class WhenFetchingForCredentialsTestCase {

        @Test
        fun `returns all the credentials if no search criteria is provided`() {
            val credentials = generateCredentials(5)
            every { credentialService.fetch(null, null) } returns credentials
            Given {
                header("content-type", "application/json")
            } When {
                get("/credentials")
            } Then {
                statusCode(200)
                body("$.size()", equalTo(credentials.size))
            }
        }

        @Test
        fun `returns empty list if no credential matches the search criteria`() {
            val url = "http://url.com"
            val username = "slimoux"

            every { credentialService.fetch(url, username) } returns emptyList()
            Given {
                header("content-type", "application/json")
            } When {
                get("/credentials?url=$url&username=$username")
            } Then {
                statusCode(200)
                body("$.size()", equalTo(0))
            }
        }

        @Test
        fun `returns http 500 if something went wrong while retrieving the credentials`() {
            every { credentialService.fetch(null, null) } throws RuntimeException()
            Given {
                header("content-type", "application/json")
            } When {
                get("/credentials")
            } Then {
                statusCode(500)
            }
        }
    }
}
