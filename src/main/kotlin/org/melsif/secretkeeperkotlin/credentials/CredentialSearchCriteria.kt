package org.melsif.secretkeeperkotlin.credentials

data class CredentialSearchCriteria(val urlPattern: String? = null, val usernamePattern: String? = null) {

    companion object {
        val NONE: CredentialSearchCriteria = CredentialSearchCriteria(null, null)
    }
}