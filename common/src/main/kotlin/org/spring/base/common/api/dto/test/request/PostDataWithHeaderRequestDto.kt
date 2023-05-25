package org.spring.base.common.api.dto.test.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PostDataWithHeaderRequestDto(
    @JsonProperty("REQUEST_STRING", required = true)
    val requestString: String,
    @JsonProperty("REQUEST_DATE")
    val requestDate: String,
    @JsonProperty("REQUEST_NUMBER")
    val requestNumber: Int
)
