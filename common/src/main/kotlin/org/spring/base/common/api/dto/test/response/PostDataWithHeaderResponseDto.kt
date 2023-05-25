package org.spring.base.common.api.dto.test.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PostDataWithHeaderResponseDto(
    @JsonProperty("RESPONSE_STATUS")
    val responseStatus: Int,
    @JsonProperty("RESPONSE_STRING")
    val responseString: String
)
