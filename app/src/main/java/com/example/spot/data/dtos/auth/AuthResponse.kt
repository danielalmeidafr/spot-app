package com.example.spot.data.dtos.auth

data class AuthResponse(
    val id: String,
    val accessToken: String,
    val refreshToken: String
)