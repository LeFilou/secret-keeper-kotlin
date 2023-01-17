package org.melsif.secretkeeperkotlin.credentials

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.melsif.secretkeeperkotlin.util.Generator.Companion.generateCredential
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.stream.Stream

@DataJpaTest
class CredentialServiceIntegrationTest {

    @Autowired
    private lateinit var credentialRepository: CredentialRepository
    private lateinit var credentialService: CredentialService

    @BeforeEach
    fun setUp() {
        val credential1 = generateCredential(url = "https://url1.com", username = "username1")
        val credential2 = generateCredential(url = "https://url1.com", username = "username2")
        val credential3 = generateCredential(url = "https://url1.com", username = "username3")
        val credential4 = generateCredential(url = "https://url2.com", username = "username1")
        val credential5 = generateCredential(url = "https://url3.com", username = "username4")
        Stream.of(credential1, credential2, credential3, credential4, credential5)
            .forEach { credentialRepository.save(it) }
        credentialService = CredentialServiceImpl(credentialRepository)
    }

    @Nested
    inner class WhenSearchingForCredentials {

        @Test
        fun `retrieves all the credentials if no criteria is given`() {
            val credentials = credentialService.fetch(CredentialSearchCriteria.NONE)
            assertThat(credentials).isNotEmpty.hasSize(5)
        }

        @Test
        fun `retrieves only the credentials that have the same url as in the search criteria`() {
            val credentials =
                credentialService.fetch(CredentialSearchCriteria(urlPattern = "https://url1.com"))
            assertThat(credentials)
                .isNotEmpty
                .hasSize(3)
                .extracting<String> { it.credentialIdentifiers.url }
                .containsOnly("https://url1.com")
        }

        @Test
        fun `retrieves only the credentials that have the same username as in the search criteria`() {
            val credentials =
                credentialService.fetch(CredentialSearchCriteria(usernamePattern = "username1"))
            assertThat(credentials)
                .isNotEmpty
                .hasSize(2)
                .extracting<String> { it.credentialIdentifiers.username }
                .containsOnly("username1")
        }

        @Test
        fun `retrieves only the credentials that have both the same username and url as in the search criteria`() {
            val credentials =
                credentialService.fetch(CredentialSearchCriteria(urlPattern = "url1", usernamePattern = "username1"))
            assertThat(credentials)
                .isNotEmpty
                .hasSize(1)
                .extracting<CredentialIdentifiers> { it.credentialIdentifiers }
                .containsOnly(CredentialIdentifiers("https://url1.com", "username1"))
        }
    }
}