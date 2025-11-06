package com.example.spot.data.dtos.schedules

data class AppointmentResponse(
    val appointments: List<AppointmentDto>,
    val month: Int
)
