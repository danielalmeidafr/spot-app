package com.example.spot.ui.presentation.main_screen.schedules.model

import java.time.LocalDate

sealed class ScheduleState{
    data object Loading: ScheduleState()
    data class Success(
        val year: Int = LocalDate.now().year,
        val month: Int = LocalDate.now().monthValue,
        val appointments: List<AppointmentData>
    ): ScheduleState()
}