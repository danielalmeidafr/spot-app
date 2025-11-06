package com.example.spot.data.dtos.schedules

data class AppointmentDto(
    val id: String,
    val title: String,
    val establishment: String,
    val day: Int,
    val month: String,
    val time: String,
    val isPaid: Boolean,
    val price: Float
)