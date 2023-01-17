package org.melsif.secretkeeperkotlin.credentials

import org.melsif.secretkeeperkotlin.credentials.CredentialSpecifications.urlContains
import org.melsif.secretkeeperkotlin.credentials.CredentialSpecifications.usernameContains
import org.springframework.stereotype.Service

@Service
class CredentialServiceImpl(val credentialRepository: CredentialRepository) : CredentialService {

    override fun fetch(credentialSearchCriteria: CredentialSearchCriteria): List<Credential> {
        val url = credentialSearchCriteria.urlPattern
        val username = credentialSearchCriteria.usernamePattern
        return credentialRepository.findAll(
            urlContains(url)
                .and(usernameContains(username))
        )
    }

}

