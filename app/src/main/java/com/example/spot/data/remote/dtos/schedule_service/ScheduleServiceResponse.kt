package com.example.spot.data.remote.dtos.schedule_service

import com.example.spot.data.remote.dtos.details.PaymentMethodsResponse

data class ScheduleServiceResponse(
    val attendants: List<AttendantResponse>,
    val availableDays: List<String>,
    val availableHours: List<String>,
    val offeredService: ServiceResponse,
    val totalPrice: Double,
    val paymentMethods: List<PaymentMethodsResponse>
)
