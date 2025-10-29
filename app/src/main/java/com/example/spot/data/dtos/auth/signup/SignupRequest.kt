package com.example.spot.data.dtos.auth.signup

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "CUSTOMER"
)