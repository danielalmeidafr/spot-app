package com.example.spot.ui.presentation.main_screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.dtos.home.establishment.EstablishmentRepository
import com.example.spot.data.dtos.home.establishment.toEstablishmentData
import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData
import com.example.spot.ui.presentation.main_screen.home.model.HomeState
import com.example.spot.ui.presentation.main_screen.home.model.NextScheduleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class HomeViewModel : ViewModel() {

    private val repository = EstablishmentRepository()

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    private var cachedEstablishments: List<EstablishmentData> = emptyList()

    init {
        viewModelScope.launch {
            try {
                val establishments = fetchEstablishments()
                cachedEstablishments = establishments

                val listTitle = if (establishments.isEmpty()) "" else "Recomendadas:"
                val nextSchedule = fetchNextSchedule()

                _state.update {
                    HomeState.Success(
                        nextScheduleData = nextSchedule,
                        listTitle = listTitle,
                        establishmentsData = establishments
                    )
                }
            } catch (e: IOException) {
                _state.update { HomeState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { HomeState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { HomeState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    private suspend fun fetchEstablishments(searchQuery: String? = null): List<EstablishmentData> {
        val establishmentsDto = repository.getAllEstablishments(name = searchQuery)
        return establishmentsDto.map { it.toEstablishmentData() }
    }

    fun updateSearchQuery(newQuery: String) {
        _state.update { currentState ->
            if (currentState is HomeState.Success) {
                return@update currentState.copy(searchQuery = newQuery)
            } else {
                currentState
            }
        }

        viewModelScope.launch {
            try {
                delay(300)

                val establishments = if (newQuery.isBlank()) cachedEstablishments else fetchEstablishments(newQuery)
                val listTitle = if (newQuery.isBlank()) "Recomendadas:" else "Resultados da busca:"

                _state.update { currentState ->
                    if (currentState is HomeState.Success) {
                        if (currentState.searchQuery == newQuery) {
                            currentState.copy(
                                listTitle = listTitle,
                                establishmentsData = establishments
                            )
                        } else {
                            currentState
                        }

                    } else {
                        currentState
                    }
                }
            } catch (e: IOException) {
                _state.update { HomeState.Error("Falha na conexão") }
            } catch (e: HttpException) {
                _state.update { HomeState.Error("Erro no servidor") }
            } catch (e: Exception) {
                _state.update { HomeState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }

    private fun fetchNextSchedule(): NextScheduleData {
        return NextScheduleData(nextScheduleTime = "Hoje, 16h00")
    }
}
