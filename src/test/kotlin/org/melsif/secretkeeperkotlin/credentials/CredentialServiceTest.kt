package org.melsif.secretkeeperkotlin.credentials

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.melsif.secretkeeperkotlin.credentials.CredentialSearchCriteria.Companion.NONE
import org.melsif.secretkeeperkotlin.util.Generator.Companion.generateCredentials


@ExtendWith(MockKExtension::class)
class CredentialServiceTest {

    private lateinit var credentialRepository: CredentialRepository
    private lateinit var credentialService: CredentialService

    @BeforeEach
    fun setUp() {
        mockkObject(CredentialSpecifications)
        credentialRepository = mockk()
        credentialService = CredentialServiceImpl(credentialRepository)
    }

    @Test
    fun `retrieves credentials`() {
        val credentials = generateCredentials(5)
        every {
            credentialRepository.findAll(
                CredentialSpecifications.urlContains(null)
                    .and(CredentialSpecifications.usernameContains(null))
            )
        } returns credentials
        val credentialDetails = credentialService.fetch(NONE)
        assertThat(credentialDetails)
            .isNotEmpty
            .hasSize(credentials.size)
    }
}