package com.example.spot.data.remote.dtos.schedules

import com.example.spot.data.remote.dtos.confirm_payment.Status
import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData

fun AppointmentDto.toAppointmentData(): AppointmentData {
    return AppointmentData(
        title = title,
        location = establishment,
        day = day,
        month = month,
        time = time,
        status = status.toStatus(),
        price = price
    )
}

fun String.toStatus(): Status {
    return when (this.uppercase()) {
        "CONFIRMED" -> Status.CONFIRMED
        "FINISHED" -> Status.FINISHED
        "CANCELED" -> Status.CANCELED
        else -> Status.CANCELED
    }
}