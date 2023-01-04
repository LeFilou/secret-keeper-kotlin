package org.melsif.secretkeeperkotlin.util

import org.jeasy.random.EasyRandom
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails


class Generator {

    companion object {

        private val generator = EasyRandom()

        fun generateString(): String {
            return generator.nextObject(String::class.java)
        }

        fun generateCredentials(size: Int): List<CredentialDetails> {
            return generateCollection(size)
        }
        private inline fun<reified T> generateCollection(size: Int): List<T> {
            return generator.objects(T::class.java, size).toList()
        }


    }
}