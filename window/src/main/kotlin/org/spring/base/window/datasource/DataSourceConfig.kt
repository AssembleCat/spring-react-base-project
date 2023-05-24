package org.spring.base.window.datasource

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * Data Source 정의 class.<br>
 *
 * DB접근에 관련된 Bean설정을 가지고 있으며 해당 Package의 역할을 벗어나서 작성할경우
 * Spring Component Scan에 포함되지 않을 수 있음.<br>
 *
 * TODO: 패키지명 변경시 basePackages의 경로 변경 필요.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "baseEntityManagerFactory",
    transactionManagerRef = "baseTransactionManager",
    basePackages = ["org.spring.base.core.domain"]
)
class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.base")
    fun baseDataSourceProperties() = DataSourceProperties()

    @Primary
    @Bean(name = ["baseDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.base.hikari")
    fun baseDataSource(): DataSource {
        return baseDataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Primary
    @Bean(name = ["baseEntityManagerFactory"])
    fun baseEntityManagerFactory(
        entityManagerFactoryBuilder: EntityManagerFactoryBuilder,
        @Qualifier("baseDataSource") baseDataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean {
        return entityManagerFactoryBuilder
            .dataSource(baseDataSource)
            .packages("org.spring.base")
            .persistenceUnit("base")
            .build()
    }

    @Primary
    @Bean(name = ["baseTransactionManager"])
    fun baseTransactionManager(
        @Qualifier("baseEntityManagerFactory") baseEntityManagerFactory: EntityManagerFactory,
    ): PlatformTransactionManager? {
        return JpaTransactionManager(baseEntityManagerFactory)
    }

}

