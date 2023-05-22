fun gradleProperty(key: String): String = providers.gradleProperty(key).get()

rootProject.name = gradleProperty("project.name")
include("app")
include("window")
include("core")
include("common")

pluginManagement {

    fun gradleProperty(key: String): String = providers.gradleProperty(key).get()

    resolutionStrategy {
        eachPlugin {
            // Case 1. Kotlin plugin
            // Set the version of the Kotlin Gradle plugin to use when the 'kotlin' plugin is applied without a version.
            if (requested.id.id.startsWith("org.jetbrains.kotlin") && requested.version == null) {
                useVersion(gradleProperty("project.version.kotlin"))
            }
            // Case 2. Spring Dependency Management plugin
            else if (requested.id.id == "io.spring.dependency-management") {
                useVersion(gradleProperty("project.version.springDependencyManager"))
            }
            // Case 3. Spring Boot plugin
            else if (requested.id.id == "org.springframework.boot") {
                useVersion(gradleProperty("project.version.springBoot"))
            }
        }
    }
}
include("core")
