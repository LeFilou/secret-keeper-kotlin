package org.melsif.secretkeeperkotlin.api

import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialsApi
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CredentialsController : CredentialsApi {

    override fun getCredentials(url: String?, username: String?): ResponseEntity<MutableList<CredentialDetails>> {
        return ResponseEntity.status(NOT_IMPLEMENTED).build()
    }

}