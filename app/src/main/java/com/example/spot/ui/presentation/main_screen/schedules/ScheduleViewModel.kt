package com.example.spot.ui.presentation.main_screen.schedules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AppointmentData(
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val isPaid: Boolean,
    val price: String? = null
)

data class CalendarUiState(
    val year: Int = LocalDate.now().year,
    val month: Int = LocalDate.now().monthValue,
    val appointments: List<AppointmentData> = emptyList(),
    val isLoading: Boolean = true
)

class ScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState = _uiState.asStateFlow()

    fun selectMonth(month: Int) {
        _uiState.value = _uiState.value.copy(month = month)
    }

    fun loadAppointments() {
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
    }
}
