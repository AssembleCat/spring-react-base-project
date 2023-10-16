package org.spring.base.build.util

import java.util.concurrent.TimeUnit

object CommandUtility {
    /**
     * Execute command by given timeout and return result
     *
     * @param command
     * @param timeout
     * @return
     */
    fun executeCommand(command: String, timeout: Long = 5): String {
        val process = Runtime.getRuntime().exec(command)
        process.waitFor(timeout, TimeUnit.SECONDS)
        val result = String(process.inputStream.readBytes())
        process.inputStream.close()
        return result
    }

    /**
     * Open folder by given path - os variation is supported
     */
    fun openFolder(path: String) {
        val os = System.getProperty("os.name").lowercase()
        if (os.contains("win")) {
            Runtime.getRuntime().exec("explorer.exe /select, $path")
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec("open $path")
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            Runtime.getRuntime().exec("xdg-open $path")
        } else {
            println("Unsupported OS($os) to open folder $path")
        }
    }
}
