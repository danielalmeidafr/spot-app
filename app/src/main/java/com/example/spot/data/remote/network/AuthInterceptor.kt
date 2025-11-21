package com.example.spot.data.remote.network

import com.example.spot.data.remote.dtos.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val repository: UserPreferencesRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            repository.accessToken.first()
        }

        var request = chain.request()

        if (token.isNotEmpty()) {
            request = request.newBuilder()
                .header(name = "Authorization", value = "Bearer $token")
                .build()
        }

        return chain.proceed(request)
    }
}