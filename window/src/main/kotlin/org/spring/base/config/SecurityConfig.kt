package org.spring.base.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * CORS 해결/API 기본 보안 해제를 위한 설정
 *
 * TODO: 초기개발시 유연하지만 보안관련 이슈가 있을 수 있음.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf().disable()
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .headers().frameOptions().sameOrigin()
        .and()
        .httpBasic().disable()
        .formLogin().disable()
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .build()

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer = WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring().antMatchers("/favicon.ico")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        return CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = true
            maxAge = 3600L
        }.let {
            UrlBasedCorsConfigurationSource().apply { registerCorsConfiguration("/**", it) }
        }
    }
}
