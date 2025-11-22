package com.example.spot.data.remote.dtos.details

data class Attendants(
    val id: String,
    val establishmentId: String,
    val profile: ProfileResponse,
    val available: Boolean
)