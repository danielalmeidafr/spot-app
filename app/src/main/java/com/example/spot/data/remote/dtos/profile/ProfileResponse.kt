package com.example.spot.data.remote.dtos.profile

import com.example.spot.data.remote.dtos.details.GenderTypes

data class ProfileResponse(
    val id: String,
    val nickName: String,
    val fullName: String,
    val birthDate: String,
    val gender: GenderTypes,
    val profileImageUrl: String,
    val userId: String
)
