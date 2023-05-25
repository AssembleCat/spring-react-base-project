package org.spring.base.common.util.cli

import java.util.concurrent.TimeUnit

object CommandUtility {
    /**
     * @param command Command String
     * @param timeout
     * @return Response String
     */
    fun executeCommand(command: String, timeout: Long = 5): String {
        val process = Runtime.getRuntime().exec(command)
        process.waitFor(timeout, TimeUnit.SECONDS)
        val result = String(process.inputStream.readBytes())
        process.inputStream.close()
        return result
    }
}
