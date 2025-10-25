plugins {
    java
    id("com.vanniktech.maven.publish") version "0.30.0"
    id("io.github.sgtsilvio.gradle.metadata") version "0.6.0"
}

group = "zm.hashcode"
version = "0.1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5 (Jupiter)
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(platform("org.testcontainers:testcontainers-bom:1.20.3"))
    implementation("org.testcontainers:testcontainers")

    // SLF4J implementation for logging
    testImplementation("org.slf4j:slf4j-simple:2.0.9")

    // Apache Commons dependencies
    implementation("org.apache.commons:commons-compress:1.26.0")
    implementation("commons-codec:commons-codec:1.16.0")
}

tasks.test {
    useJUnitPlatform()

    // Show test output in console
    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

metadata {
    readableName = "Rafiki Testcontainers"
    description = "Testcontainers wrapper for Rafiki - Interledger open payments backend for integration testing"
    license { apache2() }
    developers {
        register("boniface") {
            fullName.set("Boniface Kabaso")
            email.set("550236+boniface@users.noreply.github.com")
        }
    }
    github {
        org.set("hashcode-zm")
        repo.set("rafiki-testcontainers")
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates("zm.hashcode", "rafiki-testcontainers", "0.1.0")

    pom {
        name.set("Rafiki Testcontainers")
        description.set("Testcontainers wrapper for Rafiki - Interledger open payments backend for integration testing")
        url.set("https://github.com/hashcode-zm/rafiki-testcontainers")

        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }

        developers {
            developer {
                id.set("boniface")
                name.set("Boniface Kabaso")
                email.set("550236+boniface@users.noreply.github.com")
            }
        }

        scm {
            url.set("https://github.com/hashcode-zm/rafiki-testcontainers")
            connection.set("scm:git:git://github.com/hashcode-zm/rafiki-testcontainers.git")
            developerConnection.set("scm:git:ssh://git@github.com:hashcode-zm/rafiki-testcontainers.git")
        }
    }
}
