package org.spring.base.common.util.timeAndDate

import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtil {
    private val formatYyMmDd = DateTimeFormatter.ofPattern("yyMMdd")
    private val formatYyyyMmDd = DateTimeFormatter.ofPattern("yyyyMMdd")

    /**
     * @param date1 검사대상
     * @param date2 비교대상
     * @return 1이 2초과 = 1 / 1과 2 같음 = 0 / 1이 2미만 = -1
     */
    fun compare(date1: String, date2: String): Int {
        return if (BigDecimal(date1) < BigDecimal(date2)) {
            1
        } else {
            if (BigDecimal(date1) == BigDecimal(date2)) {
                0
            } else {
                -1
            }
        }
    }
    /**
     * @param sttDt 시작
     * @param currDt 검사대상
     * @param endDt 끝
     * @return 포함: true, 제외: false
     */
    fun between(sttDt: String, currDt: String, endDt: String): Boolean {
        return BigDecimal(currDt) in BigDecimal(sttDt)..BigDecimal(endDt)
    }
    /**
     * @param date 일자(YYYYMMDD)
     * @return Day to Number 일(1)~토(7)
     */
    fun dayOfWeekToNumber(date: String): String {
        return when (LocalDate.parse(date, formatYyyyMmDd).dayOfWeek) {
            DayOfWeek.MONDAY -> "2"
            DayOfWeek.TUESDAY -> "3"
            DayOfWeek.WEDNESDAY -> "4"
            DayOfWeek.THURSDAY -> "5"
            DayOfWeek.FRIDAY -> "6"
            DayOfWeek.SATURDAY -> "7"
            DayOfWeek.SUNDAY -> "1"
            else -> "0"
        }
    }
    /**
     * @param date 일자(YYYYMMDD)
     * @param sep 구분자
     * @return 1999-12-26
     */
    fun sepFormatDate(date: String, sep: String): String {
        return if (date.length == 8) {
            "${date.substring(0, 4)}${sep}${date.substring(4, 6)}${sep}${date.substring(6, 8)}"
        } else {
            date
        }
    }
    fun nowYyyyMmDd(): String {
        return LocalDate.now().format(formatYyyyMmDd)
    }

    /**
     * @param dateString 대상일자(YYYYMMDD) Nullable
     * @return dateString - 1일 / 요청 없을시 오늘 - 1일
     */
    fun prevDayYyyyMmDd(dateString: String?): String {
        return if (dateString.isNullOrBlank()) {
            LocalDate.now().minusDays(1L).format(formatYyyyMmDd)
        } else {
            LocalDate.parse(dateString).minusDays(1L).format(formatYyyyMmDd)
        }
    }
    /**
     * @param dateString 대상일자(YYYYMMDD) Nullable
     * @return dateString - 1달 / 요청 없을시 오늘 - 1달
     */
    fun prevMonthYyyyMmDd(dateString: String?): String {
        return if (dateString.isNullOrBlank()) {
            LocalDate.now().minusMonths(1L).format(formatYyyyMmDd)
        } else {
            LocalDate.parse(dateString).minusMonths(1L).format(formatYyyyMmDd)
        }
    }
    /**
     * @param dateString 대상일자(YYYYMMDD) Nullable
     * @return dateString - 1년 / 요청 없을시 오늘 - 1년
     */
    fun prevYearYyyyMmDd(dateString: String?): String {
        return if (dateString.isNullOrBlank()) {
            LocalDate.now().minusYears(1L).format(formatYyyyMmDd)
        } else {
            LocalDate.parse(dateString).minusYears(1L).format(formatYyyyMmDd)
        }
    }
    fun toYyMmDd(date: LocalDate): String {
        return date.format(formatYyMmDd)
    }
    fun toYyyyMmDd(date: LocalDate): String {
        return date.format(formatYyyyMmDd)
    }
}
