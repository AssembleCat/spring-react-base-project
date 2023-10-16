plugins {
    kotlin("plugin.jpa")
    application
}

version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("${project.properties["project.group"] as String}.ApplicationKt")
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")

    // Spring Data JPA
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    // Hibernate
    api ("org.hibernate:hibernate-spatial")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.6")
    api("org.slf4j:slf4j-api:2.0.5")

    // Swagger -> Maybe Spring Rest Docs?
    api("org.springdoc:springdoc-openapi-ui:1.6.15")
    api("org.springdoc:springdoc-openapi-webmvc-core:1.6.15")

    // Gson
    api("com.google.code.gson:gson")

    // retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    //implementation("com.squareup.retrofit2:converter-jaxb:2.9.0") // xml
}
