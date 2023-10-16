import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.ecr.AmazonECRClientBuilder
import org.spring.base.build.util.VersionUtility
import org.spring.base.build.util.phase
import java.util.*

plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib") version "3.3.2"
    application
}

dependencies { implementation(project(":window")) }

application {
    // Define the main class for the application.
    mainClass.set("${project.properties["project.group"] as String}.ApplicationKt")
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.google.cloud.tools:jib-gradle-plugin:3.3.2")
    }
}

jib {
    val phase = project.phase
    println(phase.name)
    val version = VersionUtility.getVersion(File("${project.rootDir}/metadata/version.yaml"), phase.name).version
    from {
        image = "mcr.microsoft.com/playwright/java:v1.34.0-jammy"
    }
    to {
        image = "833211208306.dkr.ecr.ap-northeast-2.amazonaws.com/narae-house/chatting-server"
        tags = setOf(version, phase.name)
        auth {
            username = getEcrCredential().first
            password = getEcrCredential().second
        }
    }
    container {
        mainClass = "org.spring.base.ApplicationKt"
        ports = listOf("8080")
        jvmFlags = listOf(
            "-Dspring.profiles.active=${phase.name.toLowerCase()}",
            "-Duser.timezone=Asia/Seoul"
        )
    }
}

fun getEcrCredential(): Pair<String, String> {
    val ecrClient = AmazonECRClientBuilder.standard()
        .withCredentials(
            object : AWSCredentialsProvider {
                override fun getCredentials(): AWSCredentials = getAwsCredential()

                override fun refresh() = Unit
            })
        .withRegion("ap-northeast-2")
        .build()

    val request = com.amazonaws.services.ecr.model.GetAuthorizationTokenRequest()

    val response = ecrClient.getAuthorizationToken(request)
    val token = response.authorizationData[0].authorizationToken
    val decoded = String(Base64.getDecoder().decode(token)).split(":")
    val username = decoded[0]
    val password = decoded[1]

    return Pair(username, password)
}

fun getAwsCredential(): AWSCredentials = object : AWSCredentials {
    override fun getAWSAccessKeyId(): String =
        project.properties["deploy.aws.ecr.accessKey"] as String?
            ?: throw RuntimeException("AWS Access Key is not set.")

    override fun getAWSSecretKey(): String =
        project.properties["deploy.aws.ecr.secretKey"] as String?
            ?: throw RuntimeException("AWS Secret Key is not set.")
}
