package org.melsif.secretkeeperkotlin.credentials

import org.springframework.data.jpa.domain.Specification
import java.util.*

internal object CredentialSpecifications {

    fun urlContains(url: String?): Specification<Credential> {
        return Specification<Credential> { root, _, cb ->
            if (url.isNullOrBlank()) cb.isTrue(cb.literal(true))
            else
                cb.like(
                    cb.lower(
                        root.get(Credential_.credentialIdentifiers).get(CredentialIdentifiers_.URL)
                    ), getLikePattern(url)
                )
        }
    }

    fun usernameContains(username: String?): Specification<Credential> {
        return Specification<Credential> { root, _, cb ->
            if (username.isNullOrBlank()) cb.isTrue(cb.literal(true))
            else
                cb.like(
                    cb.lower(
                        root.get(Credential_.credentialIdentifiers).get(CredentialIdentifiers_.USERNAME)
                    ), getLikePattern(username)
                )
        }
    }

    private fun getLikePattern(searchTerm: String): String {
        return "%" + searchTerm.lowercase(Locale.getDefault()) + "%"
    }
}