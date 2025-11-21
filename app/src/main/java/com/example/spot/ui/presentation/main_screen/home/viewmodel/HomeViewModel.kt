package com.example.spot.ui.presentation.main_screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.data.remote.dtos.UserPreferencesRepository
import com.example.spot.data.remote.dtos.home.establishment.EstablishmentRepository
import com.example.spot.data.remote.dtos.home.establishment.toEstablishmentData
import com.example.spot.data.remote.dtos.home.nextschedule.NextScheduleRepository
import com.example.spot.data.remote.dtos.home.nextschedule.toNextScheduleData
import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData
import com.example.spot.ui.presentation.main_screen.home.model.HomeState
import com.example.spot.ui.presentation.main_screen.home.model.NextScheduleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

class HomeViewModel(
    private val establishmentRepository: EstablishmentRepository,
    private val nextScheduleRepository: NextScheduleRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()
    private var currentToken: String = ""

    private var cachedEstablishments: List<EstablishmentData> = emptyList()

    init {
        viewModelScope.launch {
            userPreferencesRepository.accessToken.collect { accessToken ->
                currentToken = accessToken

                if (_state.value is HomeState.Error) return@collect

                if (_state.value !is HomeState.Loading) {
                    _state.update { HomeState.Loading }
                }

                try {
                    val establishments = fetchEstablishments()
                    cachedEstablishments = establishments

                    val listTitle = if (establishments.isEmpty()) "" else "Recomendadas:"

                    val nextSchedule = fetchNextSchedule(accessToken)

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
                    val message = try {
                        val errorBody = e.response()?.errorBody()?.string()
                        val json = JSONObject(errorBody ?: "")

                        json.optString("message", "Erro no servidor")
                    } catch (_: Exception) {
                        "Erro no servidor"
                    }
                    _state.update { HomeState.Error(message) }
                } catch (e: Exception) {
                    _state.update { HomeState.Error("Ocorreu um erro: ${e.message}") }
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val currentQuery = (_state.value as? HomeState.Success)?.searchQuery ?: ""

                val establishments =
                    fetchEstablishments(currentQuery.ifBlank { null })
                cachedEstablishments = establishments
                val nextSchedule = fetchNextSchedule(currentToken)

                val listTitle =
                    if (currentQuery.isNotBlank()) "Resultados da busca:" else if (establishments.isEmpty()) "" else "Recomendadas:"

                _state.update {
                    HomeState.Success(
                        searchQuery = currentQuery,
                        nextScheduleData = nextSchedule,
                        listTitle = listTitle,
                        establishmentsData = establishments
                    )
                }
            } catch (e: Exception) {
                _state.update { HomeState.Error("Ocorreu um erro ao recarregar a página") }

            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private suspend fun fetchNextSchedule(accessToken: String): NextScheduleData {
        if (accessToken.isEmpty()) {
            return NextScheduleData(nextScheduleTime = null)
        }

        return try {
            val nextScheduleDto = nextScheduleRepository.getNextSchedule()
            nextScheduleDto.toNextScheduleData()
        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 404) {
                NextScheduleData(nextScheduleTime = null)
            } else {
                throw e
            }
        }
    }

    private suspend fun fetchEstablishments(searchQuery: String? = null): List<EstablishmentData> {
        val establishmentsDto = establishmentRepository.getAllEstablishments(name = searchQuery)
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

                val establishments =
                    if (newQuery.isBlank()) cachedEstablishments else fetchEstablishments(newQuery)
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
                val message = try {
                    val errorBody = e.response()?.errorBody()?.string()
                    val json = JSONObject(errorBody ?: "")

                    json.optString("message", "Erro no servidor")
                } catch (_: Exception) {
                    "Erro no servidor"
                }

                _state.update { HomeState.Error(message) }
            } catch (e: Exception) {
                _state.update { HomeState.Error("Ocorreu um erro: ${e.message}") }
            }
        }
    }
}