package org.melsif.secretkeeperkotlin

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.jdbc.Sql

@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = ["classpath:db/remove-credentials.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:db/create-credentials.sql"])
class SecretKeeperAcceptanceTest : AcceptanceTest() {

    @Nested
    inner class FetchForCredentialsTestCase {

        @Test
        fun `retrieves all the credentials if no search criteria is provided`() {
            When {
                get("api/credentials/")
            } Then {
                statusCode(OK.value())
                body("size()", `is`(5))
            }
        }

        @Test
        fun `retrieves only the credentials who meet the search criteria`() {
            val urlPattern = "url1"
            val usernamePattern = "name1"

            Given {
                queryParam("url", urlPattern)
                queryParam("username", usernamePattern)
            } When {
                get("api/credentials/")
            } Then {
                statusCode(OK.value())
                body("size()", `is`(1))
                body("[0].url", containsString(urlPattern))
                body("[0].username", containsString(usernamePattern))
            }
        }
    }

}