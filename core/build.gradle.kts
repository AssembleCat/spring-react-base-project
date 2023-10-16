plugins {
    id("java")
    application
}

group = "org.spring.base"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("${project.properties["project.group"] as String}.ApplicationKt")
}

dependencies {
    api(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}
