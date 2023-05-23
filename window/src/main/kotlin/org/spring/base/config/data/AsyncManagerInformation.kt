package org.spring.base.config.data

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
/**
 * 비동기관련 설정 DataClass
 *
 * @property enabled AsyncManager 사용여부
 * @property corePoolSize Min Thread(Core Thread)
 * @property maxPoolSize Max Thread
 * @property queueCapacity 대기열 크기(모든 쓰레드가 점유중일때 대기할 명령 수)
 * @property keepAliveSecond 쓰레드 대기시간(해당시간만큼 쓰레드에 작업이 없을경우 쓰레드풀에서 제거)
 * @property allowCoreThreadTimeOut Core Thread도 대기시간에 도달하면 쓰레드풀에서 제거할지 여부를 결정
 * @property threadNamePrefix 쓰레드 접두사

 * @see ThreadPoolTaskExecutor
 */
data class AsyncManagerInformation(
    val enabled: Boolean = false,
    val corePoolSize: Int = 2,
    val maxPoolSize: Int = 30,
    val queueCapacity: Int = 10,
    val threadNamePrefix: String = "Async-",
    val keepAliveSecond: Int = 60,
    val allowCoreThreadTimeOut: Boolean = false
)
