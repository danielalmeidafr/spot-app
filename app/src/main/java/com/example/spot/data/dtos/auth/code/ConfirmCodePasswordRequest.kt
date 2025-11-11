package com.example.spot.data.dtos.auth.code

data class ConfirmCodePasswordRequest(
    val email: String,
    val code: String
)