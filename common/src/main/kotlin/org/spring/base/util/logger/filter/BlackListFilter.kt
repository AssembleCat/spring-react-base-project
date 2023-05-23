package org.spring.base.util.logger.filter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import org.spring.base.util.logger.compareTo

private val DEFAULT_FILTER_THRESHOLD_LEVEL = Level.OFF

/**
 * 로거의 이름이 특정 Prefix(대개 패키지명)로 시작하는지 확인하여, 해당할 경우 로그를 허용하지 않는 Logback Custom [Filter]이다.
 * Prefix 목록과 필터가 적용될 최소 레벨을 설정할 수 있다.<br>
 *
 * 예시: 아래와 같이 설정하면, `org.apache`, 'com.amazonaws'로 시작하는 로거의 DEBUG 이하 로그는 허용하지 않는다.
 * ```xml
 * <filter class="BlackListFilter">
 *     <prefix>org.apache</prefix>
 *     <prefix>com.amazonaws</prefix>
 *     <filterThresholdLevel>DEBUG</filterThresholdLevel>
 * </filter>
 * ```
 * @property prefix 허용하지 않을 로거의 Prefix 목록
 * @property thresholdLevel 허용하지 않을 로거의 최소 레벨
 */
@Suppress("unused")
class BlackListFilter : Filter<ILoggingEvent>() {
    private val prefix: MutableSet<String> = mutableSetOf()
    private var thresholdLevel: Level = DEFAULT_FILTER_THRESHOLD_LEVEL

    override fun decide(event: ILoggingEvent?): FilterReply {
        if (!isStarted) {
            return FilterReply.NEUTRAL
        }

        return if (booleanArrayOf(
                prefix.any { event?.loggerName?.startsWith(it) == true },
                // 해당 로그가 thresholdLevel 이하인지 확인
                thresholdLevel.compareTo(event?.level ?: Level.OFF) >= 0
            ).all { it }
        )
            FilterReply.DENY
        else
            FilterReply.NEUTRAL
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

    fun setThresholdLevel(level: String?) {
        this.thresholdLevel = Level.toLevel(level, DEFAULT_FILTER_THRESHOLD_LEVEL)
    }


}
