package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.AttendantInfoData
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.AvailableTimesData
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.ScheduleServiceState
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.ServiceInfoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class ScheduleServiceViewModel : ViewModel() {
    private val _state = MutableStateFlow<ScheduleServiceState>(ScheduleServiceState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { ScheduleServiceState.Loading }

            try {
                val attendants = fetchAttendants()
                val availableTimes = fetchAvailableTimes()
                val serviceInfo = fetchServiceInfo()

                _state.update {
                    ScheduleServiceState.Success(
                        attendants = attendants,
                        availableTimes = availableTimes,
                        serviceInfo = serviceInfo
                    )
                }

            } catch (e: IOException) {
                _state.update { ScheduleServiceState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                val message = parseErrorMessage(e)
                _state.update { ScheduleServiceState.Error(message) }
            } catch (e: Exception) {
                _state.update { ScheduleServiceState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}


private fun fetchAttendants(): List<AttendantInfoData>{
    return listOf(
        AttendantInfoData(
            title = "Daniel"
        ),
        AttendantInfoData(
            title = "João"
        ),
        AttendantInfoData(
            title = "Davi"
        )
    )
}

private fun fetchAvailableTimes(): AvailableTimesData{
    return AvailableTimesData(
        listOf(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00"
        )
    )
}

private fun fetchServiceInfo(): ServiceInfoData {
    return ServiceInfoData(
        title = "Corte",
        price = "60,00",
        nameAttendant = "Daniel"
    )
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