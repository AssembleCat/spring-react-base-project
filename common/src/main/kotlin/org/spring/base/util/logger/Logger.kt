package org.spring.base.util.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun getLogger(clazz: Class<*>) = LoggerFactory.getLogger(clazz)

fun getLogger(name: String) = LoggerFactory.getLogger(name)

fun getLogger(): Logger {
    val invocationClass = Thread.currentThread().stackTrace.getOrNull(2)?.className?.let {
        try {
            Class.forName(it)
        } catch (e: ClassNotFoundException) {
            null
        }
    }
    return if (invocationClass != null) {
        getLogger(invocationClass)
    } else {
        getLogger("Unknown")
    }
}
