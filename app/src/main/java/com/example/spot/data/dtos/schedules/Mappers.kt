package com.example.spot.data.dtos.schedules

import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData

fun AppointmentDto.toAppointmentData(): AppointmentData {
    return AppointmentData(
        title = title,
        location = establishment,
        day = day,
        month = month,
        time = time,
        isPaid = isPaid,
        price = price
    )
}