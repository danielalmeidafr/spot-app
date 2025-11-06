package com.example.spot.ui.presentation.main_screen.schedules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.schedules.AppointmentRepository
import com.example.spot.data.dtos.schedules.toAppointmentData
import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData
import com.example.spot.ui.presentation.main_screen.schedules.model.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import java.time.LocalDate

class ScheduleViewModel(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ScheduleState>(ScheduleState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val month = LocalDate.now().monthValue
                val appointments = fetchAppointments(month)

                _state.update {
                    ScheduleState.Success(
                        appointmentsData = appointments
                    )
                }
            } catch (e: IOException) {
                _state.update { ScheduleState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }
                _state.update { ScheduleState.Error(message) }
            } catch (e: Exception) {
                _state.update { ScheduleState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun appointments(month: Int) {
        viewModelScope.launch {
            _state.value = ScheduleState.Loading
            try {
                val appointmentsData = fetchAppointments(month)
                _state.value = ScheduleState.Success(appointmentsData)
            } catch (e: Exception) {
                _state.value = ScheduleState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    private suspend fun fetchAppointments(month: Int): List<AppointmentData> {
        return try {
            val appointmentsDto = appointmentRepository.getAllAppointments(month)
            appointmentsDto.map { it.toAppointmentData() }
        } catch (e: HttpException) {
            if (e.code() == 403 || e.code() == 404) {
                emptyList()
            } else {
                throw e
            }
        }
    }
}