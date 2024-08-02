package com.hohoanghai.weatherforecast.network.interceptor

import com.hohoanghai.weatherforecast.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url.newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY).build()

        return chain.proceed(
            chain.request().newBuilder().url(originalUrl).build()
        )
    }
}