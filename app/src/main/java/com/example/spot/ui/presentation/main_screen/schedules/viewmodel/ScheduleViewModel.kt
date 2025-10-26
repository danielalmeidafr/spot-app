package com.example.spot.ui.presentation.main_screen.schedules.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData
import com.example.spot.ui.presentation.main_screen.schedules.model.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScheduleViewModel : ViewModel() {
    private val _state = MutableStateFlow<ScheduleState>(ScheduleState.Loading)
    val state = _state.asStateFlow()

    init {
        val appointments = fetchAppointments()

        _state.update {
            ScheduleState.Success(
                appointmentsData = appointments
            )
        }
    }

    private fun fetchAppointments(): List<AppointmentData> {
        return listOf(
            AppointmentData(
                title = "Corte simples",
                location = "Ale's Stylus",
                day = 14,
                month = "outubro",
                time = "14h00",
                isPaid = true
            ),
            AppointmentData(
                title = "Barba simples",
                location = "Ale's Stylus",
                day = 23,
                month = "outubro",
                time = "17h30",
                isPaid = false,
                price = "15,00"
            )
        )
    }
}