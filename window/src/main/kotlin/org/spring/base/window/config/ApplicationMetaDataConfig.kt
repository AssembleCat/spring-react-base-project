package org.spring.base.window.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.spring.base.window.config.data.ProjectInformation
import org.spring.base.window.config.data.VersionInformation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.yaml.snakeyaml.Yaml
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Application운영에 필요한 MetaData Bean 등록
 * ```
 * - VersionInformation(버전정보)
 * - ProjectInformation(프로젝트정보)
 * - NotificationInformation(알림정보)
 * ```
 * @see VersionInformation
 * @see ProjectInformation
 */
@Configuration
class ApplicationMetadataConfig(
    private val objectMapper: ObjectMapper
) {
    @Bean
    fun versionInformation(): VersionInformation {
        val version = loadResource("version.yaml")
        val currentProfile = System.getProperty("spring.profiles.active")

        val versions = Yaml().loadAll(version) as Iterable<*>
        val versionInformation = versions.map { objectMapper.convertValue(it, VersionInformation::class.java) }
            .firstOrNull { it.phase == currentProfile }

        return versionInformation ?: throw Exception("version.yaml for this profile not found: $currentProfile")
    }

    @Bean
    fun projectInformation(): ProjectInformation {
        val rawInfo = loadResource("project.yaml")
        val projectInformationMap = Yaml().load(rawInfo) as Map<*, *>
        return objectMapper.convertValue(projectInformationMap, ProjectInformation::class.java)
    }

    private fun loadResource(filePath: String): String {
        val resource = ClassPathResource("metadata/$filePath")
        val file = resource.file

        return try {
            String(Files.readAllBytes(Paths.get(file.toURI())), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw RuntimeException("Failed to read resource: $filePath", e)
        }
    }
}
