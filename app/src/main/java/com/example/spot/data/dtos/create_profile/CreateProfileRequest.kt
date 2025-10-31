package com.example.spot.data.dtos.create_profile

data class CreateProfileRequest(
    val fullName: String,
    val nickname: String,
    val birthDate: String
)