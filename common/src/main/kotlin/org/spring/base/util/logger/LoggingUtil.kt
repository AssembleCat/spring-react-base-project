package org.spring.base.util.logger

import ch.qos.logback.classic.Level

/**
 * 2개의 Level을 비교한 후, 같으면 0, this가 크면 1 작으면 -1을 반환한다.
 * ```
 * 해당 레벨이 DEBUG 이상인지 확인: level.compareTo(Level.DEBUG) >= 0
 * 해당 레벨이 WARN 미만인지 확인: level.compareTo(Level.WARN) < 0
 * LEVEL: TRACE -> DEBUG -> INFO -> WARN -> ERROR
 * ```
 * @param target 비교하고자 하는 대상 레벨
 * @return 0: 같음, 1: this가 큼, -1: this가 작음
 */
internal fun Level.compareTo(target: Level): Int {
    return this.levelInt.compareTo(target.levelInt)
}
