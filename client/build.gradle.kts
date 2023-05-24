import com.github.gradle.node.yarn.task.YarnTask

plugins {
    id("com.github.node-gradle.node") version "3.5.1"
}

val nodeVersion = project.properties["project.version.node"] as String
val npmVersion = project.properties["project.version.npm"] as String
val yarnVersion = project.properties["project.version.yarn"] as String

node {
    version.value(nodeVersion)

    npmVersion.value(npmVersion)
    yarnVersion.value(yarnVersion)
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

task("yarnBuild", YarnTask::class) {
    group = "build"
    // Install dependencies and build the project
    dependsOn("yarnInstall")
    args.empty()
    args.add("build")
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

