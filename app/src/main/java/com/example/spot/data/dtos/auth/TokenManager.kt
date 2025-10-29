package com.example.spot.data.dtos.auth

interface TokenManager {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
}