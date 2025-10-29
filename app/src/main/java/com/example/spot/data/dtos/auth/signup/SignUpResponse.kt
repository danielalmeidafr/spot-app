package com.example.spot.data.dtos.auth.signup

data class SignUpResponse(
    val id: String,
    val username: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
