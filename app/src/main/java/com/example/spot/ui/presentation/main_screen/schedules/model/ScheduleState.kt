package com.example.spot.ui.presentation.main_screen.schedules.model

sealed class ScheduleState{
    data object Loading: ScheduleState()
    data class Success(
        val appointmentsData: List<AppointmentData>
    ): ScheduleState()
    data class Error(val message: String): ScheduleState()
}