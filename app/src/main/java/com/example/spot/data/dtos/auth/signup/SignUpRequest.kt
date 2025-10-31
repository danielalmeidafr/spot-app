package com.example.spot.data.dtos.auth.signup

data class SignUpRequest(
    val email: String,
    val password: String,
    val role: String = "CUSTOMER"
)