package org.melsif.secretkeeperkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SecretKeeperKotlinApplication

fun main(args: Array<String>) {
	runApplication<SecretKeeperKotlinApplication>(*args)
}
