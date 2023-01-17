package org.melsif.secretkeeperkotlin.credentials

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CredentialRepository : JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential>