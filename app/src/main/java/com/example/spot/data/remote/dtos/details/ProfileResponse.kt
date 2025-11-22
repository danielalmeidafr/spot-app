package com.example.spot.data.remote.dtos.details

data class ProfileResponse(
    val id: String,
    val nickName: String,
    val fullName: String,
    val birthDate: String,
    val gender: GenderTypes,
    val userId: String
)
