package com.example.spot.ui.presentation.schedule_service.model

import com.example.spot.data.remote.dtos.details.PaymentMethodsResponse
import java.time.LocalDate

sealed class ScheduleServiceState {
    data object Loading : ScheduleServiceState()
    data class Error(val message: String) : ScheduleServiceState()
    data class Success(
        val attendants: List<AttendantInfoData> = emptyList(),
        val availableDays: List<LocalDate>,
        val availableTimes: AvailableHoursData,
        val serviceInfo: ServiceInfoData,
        val totalPrice: String,
        val payments: List<PaymentMethodsResponse>,
        val showLoginDialog: Boolean = false
    ) : ScheduleServiceState()

    data object PaymentSuccess : ScheduleServiceState()
}
