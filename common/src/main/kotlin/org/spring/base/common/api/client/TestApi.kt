package org.spring.base.common.api.client

import org.spring.base.common.api.dto.test.request.PostDataWithHeaderRequestDto
import org.spring.base.common.api.dto.test.response.PostDataWithHeaderResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TestApi {
    @GET("api/get/data/{pathValue}}")
    fun getDataWithPath(
        @Path("pathValue") path: String
    )
    @GET("api/get/data")
    fun getDataWithParam(
        @Query("param1") param1: String,
        @Query("param2") param2: Int,
    )
    @POST
    fun postDataWithHeaderAcceptResponse(
        @Header("Authorization") auth: String,
        @Body body: PostDataWithHeaderRequestDto
    ): Call<PostDataWithHeaderResponseDto>
    @POST
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "What-Ever: You/Need"
    )
    fun postDataWithHeaders(
        @Body body: String
    )
}
