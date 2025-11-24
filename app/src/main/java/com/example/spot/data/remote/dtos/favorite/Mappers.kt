package com.example.spot.data.remote.dtos.favorite

import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData


fun FavoriteEstablishmentResponse.toEstablishmentData(): EstablishmentData {
    return EstablishmentData(
        id = this.id,
        name = this.name,
        averageRating = this.averageRating,
        totalReviews = 0,
        isOpen = this.isOpen,
        nextDate = this.nextDate,
        location = this.location,
        distance = this.distance ?: "",
        paymentsMethods = this.paymentMethods.toPaymentsMethods(),
        banner = this.banner
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