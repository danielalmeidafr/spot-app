package com.example.spot.ui.presentation.main_screen.schedules.model

import com.example.spot.data.remote.dtos.confirm_payment.Status

data class AppointmentData(
    val title: String,
    val location: String,
    val day: Int,
    val month: String,
    val time: String,
    val status: Status,
    val price: Float? = null
)