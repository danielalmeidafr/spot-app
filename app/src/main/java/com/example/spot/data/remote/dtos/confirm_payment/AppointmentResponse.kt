package com.example.spot.data.remote.dtos.confirm_payment

data class AppointmentResponse(
    val id: String,
    val customerId: String,
    val attendantId:String,
    val establishmentId: String,
    val offeredServiceId: String,
    val paymentMethodId: String,
    val scheduledAt: String,
    val status: Status
)