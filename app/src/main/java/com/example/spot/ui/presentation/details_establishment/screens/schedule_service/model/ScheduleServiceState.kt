package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model

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
        val showLoginDialog: Boolean = false
    ) : ScheduleServiceState()
}
