package org.spring.base.util.logger.conversion

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.ANSIConstants
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase

class LevelHighlight : ForegroundCompositeConverterBase<ILoggingEvent>() {
    override fun getForegroundColorCode(event: ILoggingEvent?): String {
        return when (event?.level) {
            Level.ERROR -> ANSIConstants.RED_FG
            Level.WARN -> ANSIConstants.YELLOW_FG
            Level.INFO -> ANSIConstants.GREEN_FG
            Level.DEBUG -> ANSIConstants.BLUE_FG
            else -> ANSIConstants.DEFAULT_FG
        }
    }
}
