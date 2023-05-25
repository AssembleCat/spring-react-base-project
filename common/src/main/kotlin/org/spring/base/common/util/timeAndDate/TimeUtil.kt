package org.spring.base.common.util.timeAndDate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    private val formatHh = DateTimeFormatter.ofPattern("HH")
    private val formatHhMmSs = DateTimeFormatter.ofPattern("HHmmss")
    private val formatHhMmSss = DateTimeFormatter.ofPattern("HHmmssSSS")

    fun getNowTimeHH(): String {
        return LocalDateTime.now().format(formatHh)
    }
    fun getNowTimeHHMMSS(): String {
        return LocalDateTime.now().format(formatHhMmSs)
    }
    fun getNowTimeHHMMssSSS(): String{
        return LocalDateTime.now().format(formatHhMmSss)
    }
}
