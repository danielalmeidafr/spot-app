package com.example.spot.data.dtos.home.establishment

import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData

fun EstablishmentDto.toEstablishmentData(): EstablishmentData {
    return EstablishmentData(
        name = this.name,
        averageRating = this.averageRating,
        totalReviews = 0,
        isOpen = this.isOpen,
        nextDate = this.nextDate,
        location = this.location,
        distance = this.distance ?: "",
        paymentsMethods = this.paymentMethods.toPaymentsMethods()
    )
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