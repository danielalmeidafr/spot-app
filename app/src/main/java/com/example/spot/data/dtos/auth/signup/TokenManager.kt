package com.example.spot.data.dtos.auth.signup

interface TokenManager {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
}