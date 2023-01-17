package org.melsif.secretkeeperkotlin.util

import org.jeasy.random.EasyRandom
import org.melsif.secretkeeperkotlin.credentials.Credential
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails


class Generator {

    companion object {

        private val generator = EasyRandom()

        fun generateString(): String {
            return generator.nextObject(String::class.java)
        }

        fun generateCredential(
            url: String = generateString(),
            username: String = generateString(),
            password: String = generateString()
        ): Credential {
            return Credential.of(
                url, username, password
            )
        }

        fun generateCredentialsDetails(size: Int): List<CredentialDetails> {
            return generateCollection(size)
        }

        fun generateCredentials(size: Int): List<Credential> {
            return generateCollection(size)
        }

        private inline fun <reified T> generateCollection(size: Int): List<T> {
            return generator.objects(T::class.java, size).toList()
        }


    }
}