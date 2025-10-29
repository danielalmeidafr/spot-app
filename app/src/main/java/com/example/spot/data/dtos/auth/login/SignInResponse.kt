package com.example.spot.data.dtos.auth.login

data class SignInResponse(
    val id: String,
    val email: String, // trocar para username depois
    val accessToken: String,
    val refreshToken: String
)
