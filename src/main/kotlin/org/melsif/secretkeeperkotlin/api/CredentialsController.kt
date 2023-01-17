package org.melsif.secretkeeperkotlin.api

import org.melsif.secretkeeperkotlin.credentials.CredentialSearchCriteria
import org.melsif.secretkeeperkotlin.credentials.CredentialService
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialsApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CredentialsController(
    val credentialService: CredentialService,
    val credentialMapper: CredentialMapper
) :
    CredentialsApi {

    override fun getCredentials(url: String?, username: String?): ResponseEntity<List<CredentialDetails>> {
        val credentials = credentialService.fetch(CredentialSearchCriteria(url, username))
            .stream()
            .map(credentialMapper::toCredentialDetails)
            .toList()
        return ResponseEntity.status(200).body(credentials)
    }

}