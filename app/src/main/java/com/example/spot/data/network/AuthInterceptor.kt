package com.example.spot.data.network

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val accessToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header(name = "Authorization", value = "Bearer $accessToken")
            .build()
        return chain.proceed(request)
    }
}