package org.spring.base.common.types.api

enum class ApiStatusCode(val code: Int) {
    CONTINUE(1),
    SUCCESS(0),
    FAIL(-1),
    TIME_OUT(-300),
    EXCEPTION(-900),
    SYSTEM_ERROR(-999);
    companion object {
        fun from(s: Int): ApiStatusCode = values().find { it.code == s } ?: SYSTEM_ERROR
    }
}
