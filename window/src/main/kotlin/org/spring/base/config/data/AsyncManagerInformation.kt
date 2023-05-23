package org.spring.base.config.data

data class AsyncManagerInformation(
    val enabled: Boolean = false,
    val corePoolSize: Int = 2,
    val maxPoolSize: Int = 30,
    val queueCapacity: Int = 10,
    val threadNamePrefix: String = "Async-",
)
