@file:Suppress("DEPRECATION")

package com.example.spot.data.remote.dtos.schedule_service

import com.example.spot.ui.presentation.schedule_service.model.AttendantInfoData
import com.example.spot.ui.presentation.schedule_service.model.AvailableHoursData
import com.example.spot.ui.presentation.schedule_service.model.ServiceInfoData
import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun AttendantResponse.toAttendantInfoData(): AttendantInfoData{
    return AttendantInfoData(
        id = this.id,
        name = this.name,
        profileImage = this.profileImage
    )
}

fun ScheduleServiceResponse.toAvailableDays(): List<LocalDate> {
    return this.availableDays.mapNotNull { dateString ->
        try {
            LocalDate.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}

fun ScheduleServiceResponse.toAvailableHoursData(): AvailableHoursData {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val formattedHours = this.availableHours.mapNotNull { timeString ->
        try {
            val time = LocalTime.parse(timeString)
            time.format(formatter)
        } catch (e: Exception) {
            if (timeString.length >= 5) timeString.take(5) else null
        }
    }

    return AvailableHoursData(
        availableHours = formattedHours
    )
}

fun ScheduleServiceResponse.toServiceInfoData(): ServiceInfoData {
    return ServiceInfoData(
        title = this.offeredService.name,
        price = String.format(Locale("pt", "BR"), "%.2f", this.offeredService.price)
    )
}

fun ScheduleServiceResponse.toTotalPrice(): String{
    return String.format(Locale("pt", "BR"), "%.2f", this.totalPrice)
}

fun List<String>.toPaymentsMethods(): List<PaymentsMethods> {
    return this.mapNotNull { methodName ->
        when (methodName.uppercase()) {
            "PIX" -> PaymentsMethods.PIX
            "CASH" -> PaymentsMethods.CASH
            "CARD" -> PaymentsMethods.CARD
            else -> null
        }
    }
}