import org.spring.base.build.enum.Phase
import org.spring.base.build.util.phase

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

    // MySQL
    runtimeOnly("mysql:mysql-connector-java:8.0.32")

    // Flyway
    implementation("org.flywaydb:flyway-core:7.15.0") // Flyway migration을 SQLServer 버전과 같이 변경할 것
    implementation("org.flywaydb:flyway-mysql")
    //implementation("org.flywaydb:flyway-sqlserver") // DB 변경 시 같이 변경

    // Spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
}

// === Test Dependencies ===
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

// Gradle clean 옵션 없이 빌드가능하도록 설정
tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// ProcessResource - copy "Metadata" information to classpath
tasks.getByName<ProcessResources>("processResources") {
    //dependsOn("buildFront")
    val s = System.getProperty("file.separator")
    from("${project.rootDir}${s}metadata") { into("/metadata") }
    from("${project.rootDir}${s}migration") { into("/migration") }

    dependsOn("buildFront")
}

task("buildFront", Copy::class) {
    val currentPhase = project.phase
    when (currentPhase) {
        Phase.ALPHA,
        Phase.LOCAL -> dependsOn(project(":client").tasks.getByName("yarnBuildStaging"))

        Phase.PRODUCTION -> dependsOn(project(":client").tasks.getByName("yarnBuild"))
    }
    // Delete the old files
    delete("${project.projectDir}/src/main/resources/static")
    from("${project.rootDir}/client/dist")
    into("${project.projectDir}/src/main/resources/static")
}
