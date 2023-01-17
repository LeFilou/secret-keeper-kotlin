package org.melsif.secretkeeperkotlin.api

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.melsif.secretkeeperkotlin.credentials.Credential
import org.melsif.secretkeeperkotlin.generated.credentials.api.CredentialDetails

@Mapper(componentModel = "spring")
interface CredentialMapper {

    @Mapping(source = "credentialIdentifiers.url", target = "url")
    @Mapping(source = "credentialIdentifiers.username", target = "username")
    fun toCredentialDetails(credential: Credential): CredentialDetails
}