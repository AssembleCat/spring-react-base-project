plugins {
    id("java")
}

group = "org.spring.base"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":common"))
    // Add here for your core dependency. show me what you got!
}

tasks.test {
    useJUnitPlatform()
}
