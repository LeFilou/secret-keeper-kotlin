package org.melsif.secretkeeperkotlin.credentials

import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails

interface CredentialService {
    fun fetch(url: String?, username: String?): List<CredentialDetails>

}
