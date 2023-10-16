import com.github.gradle.node.yarn.task.YarnTask
import org.spring.base.build.util.getProperty

plugins {
    id("java")
    id("kotlin")
    id("com.github.node-gradle.node") version "5.0.0"
    application
}

application {
    // Define the main class for the application.
    mainClass.set("${project.properties["project.group"] as String}.ApplicationKt")
}

node {
    version.value(getProperty("project.version.node"))
    npmVersion.value(getProperty("project.version.npm"))
    yarnVersion.value(getProperty("project.version.yarn"))
    distBaseUrl.value("https://nodejs.org/dist")
    download.value(true)
}

buildscript {

    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.github.node-gradle:gradle-node-plugin:3.5.1")
    }
}

task("executeFrontend", YarnTask::class) {
    group = "execution"
    // Install dependencies and build the project
    dependsOn("yarnInstall")
    args.empty()
    args.add("start")
}

task("yarnBuild", YarnTask::class) {
    group = "build"
    // Install dependencies and build the project
    dependsOn("yarnInstall")
    args.empty()
    args.add("build")
}

task("yarnBuildStaging", YarnTask::class) {
    group = "build"
    // Install dependencies and build the project
    dependsOn("yarnInstall")
    args.empty()
    args.add("build:stage")
}

task("yarnBuildNonHosted", YarnTask::class) {
    group = "build"
    // Install dependencies and build the project
    dependsOn("yarnInstall")
    args.empty()
    args.add("build:nohost")
}

task("yarnInstall", YarnTask::class) {
    group = "build"
    // Install dependencies
    args.empty()
    args.add("install")
}

task("cleanAll", Delete::class) {
    group = "build"
    delete("build", "out")
}

tasks.getByName("clean") {
    dependsOn("cleanAll")
}
