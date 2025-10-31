package com.example.spot.data.dtos.auth

import com.example.spot.data.network.SpotApiService

class AuthRepository(
    private val api: SpotApiService,
    private val userPrefs: UserPreferencesRepository
) {
    suspend fun signIn(request: AuthRequest): AuthResponse {
        return api.signIn(request)
    }

    suspend fun signUp(request: AuthRequest): AuthResponse {
        return api.signUp(request)
    }

    suspend fun saveToken(token: String) {
        userPrefs.saveToken(token)
    }
}