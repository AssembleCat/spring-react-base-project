package org.spring.base.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * 설정파일(application.yml) 'project:' 이하의 설정을 따름.
 *
 * @property frontendLocation React빌드 파일의 위치
 * @property asyncManager Async설정
 */
@ConfigurationProperties(prefix = "project")
@ConstructorBinding
data class ProjectConfigurationInformation(
    val frontendLocation: String,
    val asyncManager: AsyncManagerInformation?,
)
