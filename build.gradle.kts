@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "org.melsif"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17
java.sourceSets["main"].java.srcDir("$buildDir/generated/src/main/java")

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.openapi.generator") version "6.0.0"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("kapt") version "1.5.10"
	`jvm-test-suite`
	jacoco
	id("org.sonarqube") version "3.5.0.2730"
	`maven-publish`
	idea
}

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.17.3"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// Swagger
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.4")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

	// Database
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	// Misc
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("commons-validator:commons-validator:1.7")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
	kapt("org.hibernate:hibernate-jpamodelgen:5.5.0.Final")

}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("src/main/resources/openapi/secretkeeper.yaml")
	outputDir.set("$buildDir/generated")
	configFile.set("src/main/resources/openapi/openapi-generator-config.json")
}

tasks.withType<KotlinCompile> {
	dependsOn("openApiGenerate")
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

idea {
	module {
		val kaptMain = file("build/generated/source/kapt/main")
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}

testing {
	suites {
		configureEach {
			if (this is JvmTestSuite) {
				useJUnitJupiter()
				dependencies {
					implementation("org.springframework.boot:spring-boot-starter-test")
					implementation("io.mockk:mockk:1.13.2")
					implementation("io.rest-assured:spring-mock-mvc:5.1.1")
					implementation("io.rest-assured:kotlin-extensions:5.1.1")
					implementation("io.rest-assured:spring-mock-mvc-kotlin-extensions:5.3.0")
					implementation("org.jeasy:easy-random:5.0.0")
					implementation("org.jeasy:easy-random-core:5.0.0")
				}
			}
		}
		val test by getting(JvmTestSuite::class) {
			targets {
				all {
					testTask.configure {
						finalizedBy("jacocoTestReport")
					}
				}
			}

		}

		val integrationTest by registering(JvmTestSuite::class) {
			testType.set(TestSuiteType.INTEGRATION_TEST)
			sources {
				java {
					setSrcDirs(listOf("src/integrationTest/kotlin"))
				}
			}
			dependencies {
				implementation(project(project.path))
				implementation("org.springframework.boot:spring-boot-starter-test")
				implementation("io.rest-assured:kotlin-extensions:5.1.1")
				implementation("io.rest-assured:spring-mock-mvc:5.1.1")
				implementation("org.testcontainers:junit-jupiter")
				implementation("org.testcontainers:postgresql")
			}

			targets {
				all {
					testTask.configure {
						shouldRunAfter(test)
					}
				}
			}
		}
	}
}

tasks.check {
	dependsOn(testing.suites.named("integrationTest"))
}

sonarqube {
	properties {
		property("sonar.projectKey", "LeFilou_secret-keeper-kotlin")
		property("sonar.organization", "lefilou")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.java.coveragePlugin", "jacoco")
		property("sonar.coverage.jacoco.xmlReportPaths", "${project.buildDir}/reports/jacoco/test/jacocoTestReport.xml")
		property("sonar.language", "kotlin")
		property("sonar.sources", "src/main/kotlin")
	}
}

tasks.jacocoTestReport {
	reports {
		html.required.set(true)
		xml.required.set(true)
		csv.required.set(false)
	}
	finalizedBy("jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = "0.30".toBigDecimal()
			}
		}

		rule {
			enabled = true

			element = "CLASS"

			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = "0.80".toBigDecimal()
			}

			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = "0.90".toBigDecimal()
			}

			excludes = listOf(
				"*.generated.*",
				"*.SecretKeeperKotlinApplication*",
				"*.*MapperImpl",
				"*.*_"
			)
		}
	}
}

tasks.getByName<BootJar>("bootJar") {
	archiveClassifier.set("boot")
}