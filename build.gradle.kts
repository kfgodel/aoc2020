import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    application
}

group = "info.kfogodel"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.google.guava:guava:30.1-jre")

    testImplementation(kotlin("test-junit"))
    testImplementation("info.kfgodel:jspek:1.0.1")
    testImplementation("org.assertj:assertj-core:3.18.1")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}