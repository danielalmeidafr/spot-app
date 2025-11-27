package com.example.spot.data.remote.dtos.profile

data class ProfileWrapper(
    val profile: ProfileResponse,
    val totalReviews: Int,
    val totalAppointments: Int,
    val totalFavorites: Int
)
