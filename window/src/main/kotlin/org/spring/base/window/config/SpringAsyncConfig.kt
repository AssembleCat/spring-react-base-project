package org.spring.base.window.config

import org.spring.base.window.config.data.ProjectConfigurationInformation
import org.spring.base.common.util.logger.getLogger
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Callable
import java.util.concurrent.Future

@Configuration
@ConditionalOnProperty(
    value = ["project.asyncManager.enabled"],
    havingValue = "true",
)
@EnableAsync
class SpringAsyncConfig(
    private val projectConfigurationInformation: ProjectConfigurationInformation,
) {
    private val log = getLogger()

    @Bean(name = ["threadPoolTaskExecutor"], destroyMethod = "destroy")
    fun threadPoolTaskExecutor(): ExecutorHandler = ThreadPoolTaskExecutor().apply {
        log.debug("Creating Async Task Executor... Selected Conf from: ${projectConfigurationInformation::class.qualifiedName}")

        val asyncManagerConf = projectConfigurationInformation.asyncManager ?: throw IllegalStateException("asyncManager is null")
        corePoolSize = asyncManagerConf.corePoolSize
        maxPoolSize = asyncManagerConf.maxPoolSize
        queueCapacity = asyncManagerConf.queueCapacity
        threadNamePrefix = asyncManagerConf.threadNamePrefix
        initialize()
    }.let { ExecutorHandler(it) }


    inner class ExecutorHandler(
        private val executor: AsyncTaskExecutor,
    ) : AsyncTaskExecutor {
        private val log = getLogger()

        @Deprecated("Deprecated in Java")
        override fun execute(task: Runnable, startTimeout: Long) = executor.execute(task.wrap())

        override fun execute(task: Runnable) = executor.execute(task.wrap())

        override fun submit(task: Runnable): Future<*> = executor.submit(task.wrap())

        override fun <T : Any?> submit(task: Callable<T>): Future<T> = executor.submit(task.wrap())

        private fun <T> Callable<T>.wrap(): Callable<T> = Callable {
            try {
                return@Callable this.call()
            } catch (ex: Throwable) {
                handleException(ex)
                throw ex
            }
        }

        private fun Runnable.wrap(): Runnable = Runnable {
            try {
                this.run()
            } catch (ex: Throwable) {
                handleException(ex)
            }
        }

        // function that handles exception thrown by task
        private fun handleException(ex: Throwable) {
            log.error("Caught async exception", ex)
        }

        fun destroy() {
            if (executor !is ThreadPoolTaskExecutor) return
            log.debug("Shutting down Executor ${executor.threadNamePrefix} service")
            executor.shutdown()
        }
    }

}
