package org.spring.base.common.util.logger.filter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import org.spring.base.common.util.logger.compareTo

private val DEFAULT_FILTER_THRESHOLD_LEVEL = Level.ALL

/**
 *  로거의 이름이 특정 Prefix(대개 패키지명)로 시작하는지 확인하여, 해당할 경우에만 로그를 허용하는 Logback Custom [Filter]이다.
 * Prefix 목록과 필터가 걸러내지 않을 최소 레벨을 설정할 수 있다.<br>
 *
 * 예시: 아래와 같이 설정하면, 'org.spring.base', 'org.springframework'로 시작하는 로거의 로그 또는 INFO 이상의 로그만 허용한다.
 * ```xml
 * <filter class="WhiteListFilter">
 *     <prefix>org.spring.base</prefix>
 *     <prefix>org.springframework</prefix>
 *     <thresholdLevel>INFO</thresholdLevel>
 * </filter>
 * ```
 * @property prefix 허용할 로거의 Prefix 목록
 * @property thresholdLevel 허용할 로그의 최소 레벨
 */

@Suppress("unused")
class WhiteListFilter : Filter<ILoggingEvent>() {
    private val prefix: MutableSet<String> = mutableSetOf()
    private var thresholdLevel: Level = DEFAULT_FILTER_THRESHOLD_LEVEL

    override fun decide(event: ILoggingEvent?): FilterReply {
        if (!isStarted) {
            return FilterReply.NEUTRAL
        }

        return if (booleanArrayOf(
                prefix.any { event?.loggerName?.startsWith(it) == true },
                // 해당 로그가 thresholdLevel 이상인지 확인
                (event?.level?.compareTo(thresholdLevel) ?: -1) >= 0
            ).any { it }
        )
            FilterReply.NEUTRAL
        else
            FilterReply.DENY
    }

    override fun start() {
        if (this.prefix.isNotEmpty()) {
            super.start()
        }
    }

    fun addPrefix(prefix: String?) {
        // add if prefix is not null
        prefix?.let { this.prefix.add(it) }
    }

    fun addThresholdLevel(level: String?) {
        this.thresholdLevel = Level.toLevel(level, DEFAULT_FILTER_THRESHOLD_LEVEL)
    }
}
