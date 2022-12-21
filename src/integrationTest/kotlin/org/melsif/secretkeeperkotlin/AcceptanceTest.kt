package org.melsif.secretkeeperkotlin

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
abstract class AcceptanceTest {

    companion object {
        private const val POSTGRES_IMAGE_NAME = "postgres:latest"

        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>(POSTGRES_IMAGE_NAME)

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }

        @BeforeEach
        internal fun setUp() {
            RestAssured.port = 8282
            RestAssured.baseURI = "http://localhost"
        }
    }


}