plugins {
    kotlin("plugin.jpa")
}

version = "unspecified"

repositories {
    mavenCentral()
}

// === Dependencies ===
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")

    // Spring Data JPA
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // Hibernate
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.6")
    api("org.slf4j:slf4j-api:2.0.5")

    // Swagger -> Maybe Spring Rest Docs?
    api("org.springdoc:springdoc-openapi-ui:1.6.15")
    api("org.springdoc:springdoc-openapi-webmvc-core:1.6.15")


    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    //implementation("com.squareup.retrofit2:converter-jaxb:2.9.0") // xml
}
