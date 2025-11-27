package com.example.spot.data.remote.dtos.confirm_payment

data class AppointmentRequest(
    val attendantId:String,
    val establishmentId: String,
    val offeredServiceId: String,
    val paymentMethodId: String,
    val scheduledAt: String
)