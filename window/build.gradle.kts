plugins {
    id("java")

    id("org.jetbrains.kotlin.plugin.spring")
}

version = "unspecified"

dependencies {
    api(project(":core"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // spring boot jetty
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    // spring boot devtools
    implementation("org.springframework.boot:spring-boot-devtools")
    // spring boot websocket
    //implementation("org.springframework.boot:spring-boot-starter-websocket")

    // SQL server driver
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.2.0.jre11") // DB 변경/JDK 버전 11 아래일 시 변경

    // H2
    //implementation("com.h2database:h2:2.1.214") // DB 미설치 환경에서 사용

    // Flyway
    implementation("org.flywaydb:flyway-core:7.15.0") // Flyway migration을 SQLServer 버전과 같이 변경할 것
    //implementation("org.flywaydb:flyway-sqlserver") // DB 변경 시 같이 변경

    // Spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
}

// === Test Dependencies ===
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(11)
}
