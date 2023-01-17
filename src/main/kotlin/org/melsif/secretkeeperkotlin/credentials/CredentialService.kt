package org.melsif.secretkeeperkotlin.credentials

interface CredentialService {
    fun fetch(credentialSearchCriteria: CredentialSearchCriteria): List<Credential>

}
