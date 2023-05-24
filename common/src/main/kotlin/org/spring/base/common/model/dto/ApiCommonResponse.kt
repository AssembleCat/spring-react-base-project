package org.spring.base.common.model.dto

data class ApiCommonResponse(
    val statusCode: Int,
    val message: String,
    val data: Any? = null
)
