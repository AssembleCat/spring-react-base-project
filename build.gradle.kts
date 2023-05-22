import org.gradle.configurationcache.extensions.capitalized
import org.spring.base.build.enum.Phase
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

 plugins {
    id("java")
    kotlin("jvm")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
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

    // Exclude the following dependencies from whole project
    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
        }
    }

    repositories {
        google()
        mavenCentral()
        maven {
            name = "ImtSoft-Releases"
            setUrl("https://repo.imtsoft.me/nexus/content/repositories/releases")
            credentials {
                username = "anonymous"
                password = "imtsoft2018!!"
            }
        }
        maven {
            name = "ImtSoft-Snapshots"
            setUrl("https://repo.imtsoft.me/nexus/content/repositories/snapshots")
            credentials {
                username = "anonymous"
                password = "imtsoft2018!!"
            }
        }
        maven { setUrl("https://jitpack.io") }
    }

    tasks.withType(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = project.properties["project.version.jvm"] as String
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(project.properties["project.version.jvm"] as String))
    }
}
data class DeployData(
    val phase: Phase,
    val force: Boolean = false
)

// 주의! Phase는 대소문자를 구분하지 않습니다.
val deployTypes: Map<String, DeployData> = mapOf(
    "alpha" to DeployData(Phase.ALPHA),
    "alphaForce" to DeployData(Phase.ALPHA),
    "production" to DeployData(Phase.PRODUCTION),
    "productionTest" to DeployData(Phase.PRODUCTION_TEST),
)

tasks.register("execute") {
    group = "실행 - execution"
    description = "어플리케이션을 완전히 실행합니다."
    dependsOn(":app:run")
}
