package com.example.spot.data.remote.dtos.details

data class OpeningDatesResponse(
    val id: String,
    val dayOfWeek: String,
    val openTime: String,
    val closeTime: String
)
