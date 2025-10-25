package com.example.spot.ui.presentation.main_screen.schedules.model

data class AppointmentData(
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val isPaid: Boolean,
    val price: String? = null
)
