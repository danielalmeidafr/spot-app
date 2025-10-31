package com.example.spot.data.dtos.auth

import com.example.spot.data.dtos.auth.login.SignInRequest
import com.example.spot.data.dtos.auth.login.SignInResponse
import com.example.spot.data.dtos.auth.signup.SignUpRequest
import com.example.spot.data.dtos.auth.signup.SignUpResponse
import com.example.spot.data.network.SpotApiService

class AuthRepository(
    private val api: SpotApiService,
    private val userPrefs: UserPreferencesRepository
) {
    suspend fun signIn(
        request: SignInRequest
    ) : SignInResponse {
        return api.signIn(request)
    }

    suspend fun signUp(
        request: SignUpRequest
    ) : SignUpResponse {
        return api.signUp(request)
    }

    suspend fun clearToken() {
        userPrefs.clearToken()
    }

    suspend fun saveToken(token: String) {
        userPrefs.saveToken(token)
    }
}