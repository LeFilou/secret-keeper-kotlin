package org.melsif.secretkeeperkotlin.rest

import org.melsif.secretkeeperkotlin.credentials.web.CredentialDetails
import org.melsif.secretkeeperkotlin.credentials.web.CredentialsApi
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CredentialsController : CredentialsApi {

    override fun getCredentials(url: String?, username: String?): ResponseEntity<MutableList<CredentialDetails>> {
        return ResponseEntity.status(NOT_IMPLEMENTED).build()
    }

}