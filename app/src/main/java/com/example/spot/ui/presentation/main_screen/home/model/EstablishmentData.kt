package com.example.spot.ui.presentation.main_screen.home.model

import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods

data class EstablishmentData(
    val id: String,
    val name: String,
    val averageScore: Double,
    val totalReviews: Int,
    val isOpen: Boolean,
    val nextDate: String,
    val location: String,
    val distance: String,
    val paymentsMethods: List<PaymentsMethods>,
    val banner: String?
)
