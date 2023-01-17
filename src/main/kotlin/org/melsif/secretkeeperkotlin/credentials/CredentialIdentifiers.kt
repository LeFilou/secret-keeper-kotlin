package org.melsif.secretkeeperkotlin.credentials

import org.apache.commons.validator.routines.UrlValidator
import javax.persistence.Embeddable

@Embeddable
data class CredentialIdentifiers(val url: String, val username: String) {

    init {
        if (url.isBlank() || username.isBlank()) {
            throw IllegalArgumentException(URL_OR_USERNAME_EMPTY)
        }
        if (!UrlValidator().isValid(url)) {
            throw IllegalArgumentException(URL_INVALID)
        }

    }

    companion object {
        const val URL_OR_USERNAME_EMPTY = "credentialIdentifiers.empty.fields.error"
        const val URL_INVALID = "credentialIdentifiers.invalid.url.error"
    }
}