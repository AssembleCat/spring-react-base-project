plugins {
    `kotlin-dsl`
    id("java")
}

group = "org.spring.base"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.2")

    // AWS
    implementation("com.amazonaws:aws-java-sdk-s3:1.11.73")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.apache.httpcomponents:httpcore:4.4.14")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.4.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.4.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
    implementation("com.squareup.retrofit2:converter-jaxb:2.7.2")
}
