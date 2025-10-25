package com.example.spot.ui.presentation.main_screen.schedules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData
import com.example.spot.ui.presentation.main_screen.schedules.model.ScheduleState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleState())
    val uiState = _uiState.asStateFlow()

    fun selectMonth(month: Int) {
        _uiState.value = _uiState.value.copy(month = month)
    }

    init {

    }

    private fun fetchAppointments(): List<AppointmentData> {
        return listOf(
            AppointmentData(
                title = "Corte simples",
                location = "Ale's Stylus",
                date = "14 de outubro",
                time = "14h00",
                isPaid = true
            ),
            AppointmentData(
                title = "Barba simples",
                location = "Ale's Stylus",
                date = "23 de outubro",
                time = "17h30",
                isPaid = false,
                price = "15,00"
            )
        )
    }

    /*fun loadAppointments() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(1000)

            val sampleAppointments = listOf(
                AppointmentData(
                    title = "Corte simples",
                    location = "Ale's Stylus",
                    date = "14 de outubro",
                    time = "14h00",
                    isPaid = true
                ),
                AppointmentData(
                    title = "Barba simples",
                    location = "Ale's Stylus",
                    date = "23 de outubro",
                    time = "17h30",
                    isPaid = false,
                    price = "15,00"
                )
            )
            _uiState.value = _uiState.value.copy(
                appointments = sampleAppointments,
                isLoading = false
            )
        }
    }*/


}