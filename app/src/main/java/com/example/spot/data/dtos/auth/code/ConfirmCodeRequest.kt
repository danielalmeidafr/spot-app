package com.example.spot.data.dtos.auth.code

data class ConfirmCodeRequest(
    val email: String,
    val code: String
)