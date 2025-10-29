package com.example.spot.data.dtos.auth.signup

data class SignupResponse(
    val id: String,
    val username: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)
