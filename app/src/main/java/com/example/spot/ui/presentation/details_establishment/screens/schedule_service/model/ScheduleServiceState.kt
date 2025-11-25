package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model

sealed class ScheduleServiceState {
    data object Loading : ScheduleServiceState()
    data class Error(val message: String) : ScheduleServiceState()
    data class Success(
        val attendants: List<AttendantInfoData> = emptyList(),
        val availableTimes: AvailableTimesData,
        val serviceInfo: ServiceInfoData
    ) : ScheduleServiceState()
}
