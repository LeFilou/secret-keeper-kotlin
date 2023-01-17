package org.melsif.secretkeeperkotlin.credentials

import java.time.LocalDate
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "CREDENTIALS")
class Credential private constructor(url: String, username: String, password: String) {

    @Id
    @SequenceGenerator(name = "credentials_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credentials_id_seq")
    var id: Int? = null
        private set
    @Embedded
    var credentialIdentifiers: CredentialIdentifiers
        private set

    var password: String
        private set

    var creationDate: LocalDate
        private set

    var modificationDate: LocalDate
        private set


    init {
        credentialIdentifiers = CredentialIdentifiers(url, username)
        this.password = Objects.requireNonNull(password)
        creationDate = LocalDate.now()
        modificationDate = LocalDate.now()
    }

    companion object {
        fun of(url: String, username: String, password: String): Credential {
            return Credential(url, username, password)
        }
    }

}

