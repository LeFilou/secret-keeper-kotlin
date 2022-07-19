package org.melsif.secretkeeperkotlin.acceptancetests

import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SecretKeeperAcceptanceTest : AcceptanceTest() {

    @Nested
    inner class FetchForCredentialsTestCase {

        @Test
        fun `retrieves all the credentials`() {
            When {
                get("api/credentials/")
            } Then {
              statusCode(200)
              body("size()", `is`(5))
            }
        }
    }
}