package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.schedule_service.ScheduleServiceRepository
import com.example.spot.data.remote.dtos.schedule_service.toAttendantInfoData
import com.example.spot.data.remote.dtos.schedule_service.toAvailableDays
import com.example.spot.data.remote.dtos.schedule_service.toAvailableHoursData
import com.example.spot.data.remote.dtos.schedule_service.toServiceInfoData
import com.example.spot.data.remote.dtos.schedule_service.toTotalPrice
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.ScheduleServiceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException
import java.time.LocalDate

class ScheduleServiceViewModel(
    private val repository: ScheduleServiceRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScheduleServiceState>(ScheduleServiceState.Loading)
    val state = _state.asStateFlow()

    private var currentEstablishmentId: String = ""
    private var currentServiceId: String = ""
    private var currentDate: LocalDate = LocalDate.now()

    fun loadSchedule(establishmentId: String, serviceId: String) {
        this.currentEstablishmentId = establishmentId
        this.currentServiceId = serviceId

        fetchData(currentDate)
    }

    fun onDateSelected(date: LocalDate) {
        if (currentDate != date) {
            currentDate = date
            fetchData(date)
        }
    }

    private fun fetchData(date: LocalDate) {
        viewModelScope.launch {
            _state.update { ScheduleServiceState.Loading }

            try {
                val formattedDate = date.toString()

                val response = repository.getInfoAppointment(
                    establishmentId = currentEstablishmentId,
                    offeredServiceId = currentServiceId,
                    appointmentDate = formattedDate
                )

                _state.update {
                    ScheduleServiceState.Success(
                        attendants = response.attendants.map { it.toAttendantInfoData() },
                        availableDays = response.toAvailableDays(),
                        availableTimes = response.toAvailableHoursData(),
                        serviceInfo = response.toServiceInfoData(),
                        totalPrice = response.toTotalPrice()
                    )
                }

            } catch (e: IOException) {
                _state.update { ScheduleServiceState.Error("Falha na conexão. Verifique sua internet.") }
            } catch (e: HttpException) {
                if (e.code() == 403) {
                    setLoginDialogVisibility(true)
                }
                val message = parseErrorMessage(e)
                _state.update { ScheduleServiceState.Error(message) }
            } catch (e: Exception) {
                _state.update { ScheduleServiceState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    fun dismissLoginDialog() {
        setLoginDialogVisibility(false)
    }

    private fun setLoginDialogVisibility(show: Boolean) {
        _state.update { currentState ->
            if (currentState is ScheduleServiceState.Success) {
                currentState.copy(showLoginDialog = show)
            } else {
                currentState
            }
        }
    }

    private fun parseErrorMessage(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val json = JSONObject(errorBody ?: "")
            json.optString("message", "Erro no servidor")
        } catch (_: Exception) {
            "Erro no servidor"
        }
    }
}