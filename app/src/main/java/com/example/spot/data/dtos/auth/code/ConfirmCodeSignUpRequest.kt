package com.example.spot.data.dtos.auth.code

data class ConfirmCodeSignUpRequest(
    val email: String,
    val code: String,
    val password: String,
    val role: String = "CUSTOMER"
)
