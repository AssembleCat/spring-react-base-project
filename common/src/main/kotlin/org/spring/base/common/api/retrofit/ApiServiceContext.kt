package org.spring.base.common.api.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 생성을 지원해주는 클래스
 *
 * @param baseUrl 요청서버의 URL
 * @param apiService API 동작을 정의한 Interface
 */
object ApiServiceContext {
    fun <T> createApiService(
        baseUrl: String,
        apiService: Class<T>,
        timeoutSec: Long = 60,
        networkInterceptors: List<Interceptor> = listOf(DefaultNetworkInterceptor()),
        applicationInterceptors: List<Interceptor> = listOf(defaultLoggingInterceptor())
    ): T {
        var safeBaseUrl = baseUrl
        if (!baseUrl.endsWith("/")) safeBaseUrl += "/"

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeoutSec, TimeUnit.SECONDS)
            .writeTimeout(timeoutSec, TimeUnit.SECONDS)
            .readTimeout(timeoutSec, TimeUnit.SECONDS)
            .cache(null)
            .apply { networkInterceptors.forEach { addNetworkInterceptor(it) } }
            .apply { applicationInterceptors.forEach { addInterceptor(it) } }
            .build()

        return Retrofit.Builder()
            .baseUrl(safeBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())  //String
            .addConverterFactory(JacksonConverterFactory.create())  //JSON
            //.addConverterFactory(XmlConverterFactory.create()) XML
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(apiService)
    }

    class DefaultNetworkInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)

            // If the response's code is not 2xx, throw an exception.
            if (response.code < 200 || response.code >= 300) {
                throw Exception("API Request ${request.url} failed with code ${response.code}")
            }

            return response
        }
    }

    private fun defaultLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
