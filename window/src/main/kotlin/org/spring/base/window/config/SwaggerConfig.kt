package org.spring.base.window.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.spring.base.window.config.data.ProjectInformation
import org.spring.base.window.config.data.VersionInformation
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Swagger REST API의 문서화를 제공을 위한 설정클래스
 *
 * Websocket등 비동기 API는 문서화가 되지 않음, 필요시 [AsyncAPI](https://www.asyncapi.com/)를 참고바랍니다.
 */
@Configuration
@EnableAutoConfiguration
class SwaggerConfig(
    private val versionInformation: VersionInformation,
    private val projectInformation: ProjectInformation,
) {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(createApiInfo())

    private fun createApiInfo() = Info()
        .title(projectInformation.name)
        .description(projectInformation.description)
        .version(versionInformation.version)
        .license(License())
}
