package org.melsif.secretkeeperkotlin.api

import org.melsif.secretkeeperkotlin.credentials.CredentialService
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialsApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CredentialsController(val credentialService: CredentialService) : CredentialsApi {

    override fun getCredentials(url: String?, username: String?): ResponseEntity<List<CredentialDetails>> {
        val credentialDetails: List<CredentialDetails> = credentialService.fetch(url, username)
        return ResponseEntity.status(200).body(credentialDetails)
    }

}