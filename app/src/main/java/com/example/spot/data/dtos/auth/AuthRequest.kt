package com.example.spot.data.dtos.auth

data class AuthRequest(
    val email: String,
    val password: String,
    val role: String? = null
)