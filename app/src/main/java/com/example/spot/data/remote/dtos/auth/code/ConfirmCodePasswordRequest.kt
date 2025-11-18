package com.example.spot.data.remote.dtos.auth.code

data class ConfirmCodePasswordRequest(
    val email: String,
    val code: String
)