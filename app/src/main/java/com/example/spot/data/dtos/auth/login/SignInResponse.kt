package com.example.spot.data.dtos.auth.login

data class SignInResponse(
    val id: String,
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
