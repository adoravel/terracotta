package terracotta.convention

import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm")
    `java-library`
}

kotlin {
    explicitApi()
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }
}
