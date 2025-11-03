package com.example.spot.data.dtos.auth

import com.example.spot.data.network.SpotApiService

class AuthRepository(
    private val api: SpotApiService,
    private val userPrefs: UserPreferencesRepository
) {
    suspend fun logout(request: LogoutRequest) {
        return api.logout(request)
    }

    suspend fun signIn(request: SignInRequest): AuthResponse {
        return api.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest): AuthResponse {
        return api.signUp(request)
    }

    suspend fun confirmCode(request: ConfirmCodeRequest) {
        return api.confirmCode(request)
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest) {
        return api.forgotPassword(request)
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String){
        userPrefs.saveTokens(accessToken, refreshToken)
    }
}