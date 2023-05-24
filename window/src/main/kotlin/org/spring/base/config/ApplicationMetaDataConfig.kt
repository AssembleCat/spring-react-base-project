package org.spring.base.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.spring.base.config.data.ProjectInformation
import org.spring.base.config.data.VersionInformation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml

/**
 * Application운영에 필요한 추가 정보들을 Bean 등록
 * ```
 * - VersionInformation(버전정보)
 * - ProjectInformation(프로젝트정보)
 * ```
 * @see VersionInformation
 * @see ProjectInformation
 */
@Configuration
class ApplicationMetadataConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun versionInformation(): VersionInformation {
        // Import version.yaml inside resources classpath
        val version = loadResource("version.yaml")
        // version.yaml is multiple documents in single file
        // so we need to parse it as list
        val currentProfile = System.getProperty("spring.profiles.active")
        return Yaml().loadAll(version).map { objectMapper.convertValue(it, VersionInformation::class.java) }
            .firstOrNull { it.phase == currentProfile }
            ?: throw Exception("version.yaml for this profile not found: $currentProfile")

    }

    @Bean
    fun projectInformation(): ProjectInformation {
        val rawInfo = loadResource("project.yaml")
        return Yaml().load<LinkedHashMap<*, *>>(rawInfo)
            .let { return objectMapper.convertValue(it, ProjectInformation::class.java) }
    }

    private fun loadResource(name: String) =
        javaClass.classLoader.getResource(name)?.readText()
            ?: throw Exception("Resource $name not found")

}
