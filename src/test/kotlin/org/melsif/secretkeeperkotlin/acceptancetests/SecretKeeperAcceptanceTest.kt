package org.melsif.secretkeeperkotlin.acceptancetests

import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED

class SecretKeeperAcceptanceTest : AcceptanceTest() {

    @Nested
    inner class FetchForCredentialsTestCase {

        @Test
        fun `retrieves all the credentials`() {
            When {
                get("api/credentials/")
            } Then {
              statusCode(NOT_IMPLEMENTED.value())
            }
        }
    }
}