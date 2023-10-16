import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.spring.base.build.enum.Phase
import org.spring.base.build.util.CommandUtility
import org.spring.base.build.util.phase

plugins {
    id("java")
    kotlin("jvm")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    id("com.google.cloud.tools.jib") version "3.3.2"
    application
}

allprojects {
    group = project.properties["project.group"] as String

    // === Apply common plugins ===
    // Kotlin jvm
    apply(plugin = "kotlin")
    // Spring Boot dependency management
    apply(plugin = "io.spring.dependency-management")
    // Spring Boot
    apply(plugin = "org.springframework.boot")
    // Kotlin Spring Support
    apply(plugin = "kotlin-spring")

    // Exclude the following dependencies from whole project
    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        }
    }

    tasks.withType(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = project.properties["project.version.jvm"] as String
        }
    }

    repositories {
        // Maven Central
        mavenCentral()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(project.properties["project.version.jvm"] as String))
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(project.properties["project.version.jvm"] as String))
        }
    }
}


data class DeployData(
    val phase: Phase,
    val force: Boolean = false
)

// 주의! Phase는 대소문자를 구분하지 않습니다.
val deployTypes: Map<String, DeployData> = mapOf(
    "alpha" to DeployData(Phase.ALPHA),
    "alphaForce" to DeployData(Phase.ALPHA, true),
    "production" to DeployData(Phase.PRODUCTION)
)

for (phase in Phase.values()) {
    tasks.register("setPhaseTo${phase.name.capitalize()}") {
        group = "_"
        description = "프로젝트의 환경을 ${phase.name}으로 설정합니다. 이 태스크는 단독으로 실행되지 않습니다."
        doLast {
            project.phase = phase
            println("Project phase is set to ${phase.name}.")
        }
    }
}

for (deployType in deployTypes) {

    tasks.register(deployType.key) {
        group = "배포 - deploy"
        description = "배포를 진행합니다."

        if (!deployType.value.force) {
            // TODO: Need check logic
        }
        val setPhase = dependsOn("setPhaseTo${deployType.value.phase.name.capitalize()}")
        val deploy = dependsOn(":app:jib")

    }
}

tasks.register("execute") {
    group = "실행 - execution"
    description = "어플리케이션을 완전히 실행합니다."
    dependsOn(":app:run")
}

tasks.register("buildBackend") {
    group = "빌드 - build"
    description = "백엔드 서버를 빌드합니다."
    dependsOn(":window:build")
    // Open the folder after build
    doLast {
        // Get the path of the jar file contained folder
        val path = File(tasks.getByPath(":window:bootJar").outputs.files.asPath).absolutePath
        // Open the folder
        CommandUtility.openFolder(path)
    }
}

tasks.named("jib") {
    dependsOn(":app:jibDockerBuild")
}

springBoot {
    mainClass.set("${project.properties["project.group"] as String}.ApplicationKt")
}
