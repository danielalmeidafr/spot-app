package com.example.spot.ui.presentation.main_screen.schedules.model

data class AppointmentData(
    val title: String,
    val location: String,
    val day: Int,
    val month: String,
    val time: String,
    val isPaid: Boolean,
    val price: String? = null
)
