package org.spring.base.config

import org.spring.base.config.data.ProjectConfigurationInformation
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableConfigurationProperties(ProjectConfigurationInformation::class)
class WebConfig(
    private val projectConfigurationInformation: ProjectConfigurationInformation
) : WebMvcConfigurer {
    /**
     * 정적리소스 파일 경로를 Spring에게 제공
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .addResourceLocations("file:${projectConfigurationInformation.frontendLocation}/")
    }
    /**
     * HTTP StatusCode != HttpServletResponse.SC_OK(200)
     *
     * HTTP Response의 상태코드가 200이 아닐때 에러를 전송
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(object : HandlerInterceptor {
            override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
                return try {
                    if (response.status != HttpServletResponse.SC_OK) {
                        response.sendError(response.status)
                        return false
                    } else if (response.status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
                        response.sendError(response.status, "Internal Server Error")
                    } // Extra HTTP status error handling
                    true
                } catch (e: Exception) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                    return false
                }
            }
        })
            .addPathPatterns("/api/**")
            .excludePathPatterns("Excluded path here")
    }
}
