package org.spring.base.common.util.timeAndDate

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class TimeUnit {
    SECONDS,
    MINUTES,
    HOURS,
    DAY
}
object DateTimeUtil {
    private val formatYyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val formatYyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val formatYyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")

    fun getNowDateTimeYyyyMmDd(): String {
        return LocalDateTime.now().format(formatYyyyMMdd)
    }
    fun getNowDateTimeYyyyMmDdHhMmSsSeperated(): String {
        return LocalDateTime.now().format(formatYyyyMMddHHmmss)
    }
    fun getNowDateTimeYyyyMmDdHhMmSsSss(): String {
        return LocalDateTime.now().format(formatYyyyMMddHHmmssSSS)
    }

    /**
     * @param start 시작시간
     * @param end 끝시간
     * @param term 차이를 나타낼 단위(시, 분, 초)
     * @see TimeUnit
     * @return start, end간의 차이(시, 분, 초)
     */
    fun getDiffByTimeUnit(start: LocalDateTime, end: LocalDateTime, term: TimeUnit?): Long {
        val duration = Duration.between(start, end)
        return when (term) {
            TimeUnit.SECONDS -> duration.seconds
            TimeUnit.MINUTES -> duration.toMinutes()
            TimeUnit.HOURS -> duration.toHours()
            TimeUnit.DAY -> duration.toDays()
            else -> duration.seconds
        }
    }
}
