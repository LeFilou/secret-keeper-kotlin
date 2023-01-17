package org.melsif.secretkeeperkotlin.credentials

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test
import org.melsif.secretkeeperkotlin.util.Generator.Companion.generateCredential

class CredentialTest {

    @Test
    fun `empty username is not accepted`() {
        val url = "https://url.com"
        val username = ""
        val password = "password"
        assertThatIllegalArgumentException()
            .isThrownBy { Credential.of(url, username, password) }
    }

    @Test
    fun `empty url is not accepted`() {
        val url = ""
        val username = "username"
        val password = "password"
        assertThatIllegalArgumentException()
            .isThrownBy { Credential.of(url, username, password) }
    }

    @Test
    fun `credential url must be valid`() {
        val url = "https:/url.com"
        val username = "username"
        val password = "password"
        assertThatIllegalArgumentException()
            .isThrownBy { Credential.of(url, username, password) }
    }

    @Test
    fun `dates must be set on creation`() {
        val credential = generateCredential(url = "https://url.com")
        assertThat(credential.creationDate).isToday
        assertThat(credential.modificationDate).isToday
    }

}