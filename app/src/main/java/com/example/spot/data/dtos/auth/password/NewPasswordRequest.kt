package com.example.spot.data.dtos.auth.password

data class NewPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
)