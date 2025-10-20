package com.example.spot.ui.presentation.main_screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class NextScheduleData(
    val isScheduled: Boolean,
    val nextScheduleTime: String?
)

data class EstablishmentData(
    val name: String,
    val averageRating: Double,
    val totalReviews: Int,
    val isOpen: Boolean,
    val nextDate: String,
    val location: String,
    val distance: String,
    val paymentsMethods: List<PaymentsMethods>,
)

data class HomeUiState(
    val establishments: List<EstablishmentData> = emptyList(),
    val nextSchedule: NextScheduleData = NextScheduleData(
        isScheduled = false,
        nextScheduleTime = null
    ),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val listTitle: String = ""
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val allEstablishments = MutableStateFlow<List<EstablishmentData>>(emptyList())

    init {
        loadHomeData()
        viewModelScope.launch {
            combine(allEstablishments, _uiState) { establishments, state ->
                val filteredList = if (state.searchQuery.isBlank()) {
                    establishments
                } else {
                    establishments.filter {
                        it.name.contains(state.searchQuery, ignoreCase = true) ||
                                it.location.contains(state.searchQuery, ignoreCase = true)
                    }
                }

                val newTitle = if (state.searchQuery.isBlank()) {
                    "Recomendadas:"
                } else {
                    "Resultados da busca:"
                }

                state.copy(establishments = filteredList, listTitle = newTitle)
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(3000)

            val sampleEstablishments = listOf(
                EstablishmentData(
                    name = "Studio Barber Lux",
                    averageRating = 4.8,
                    totalReviews = 310,
                    isOpen = true,
                    nextDate = "Hoje, 16h00",
                    location = "Moema",
                    distance = "3,1km",
                    paymentsMethods = listOf(
                        PaymentsMethods.PIX,
                        PaymentsMethods.CASH,
                        PaymentsMethods.CARD
                    )
                ),
                EstablishmentData(
                    name = "Salão Estilo & Beleza",
                    averageRating = 4.3,
                    totalReviews = 78,
                    isOpen = false,
                    nextDate = "Amanhã, 10h00",
                    location = "Pinheiros",
                    distance = "5,4km",
                    paymentsMethods = listOf(PaymentsMethods.PIX, PaymentsMethods.CASH)
                ),
                EstablishmentData(
                    name = "Tattoo House SP",
                    averageRating = 4.9,
                    totalReviews = 452,
                    isOpen = true,
                    nextDate = "Hoje, 18h30",
                    location = "Vila Madalena",
                    distance = "4,2km",
                    paymentsMethods = listOf(PaymentsMethods.PIX)
                ),
                EstablishmentData(
                    name = "Beleza Express",
                    averageRating = 4.1,
                    totalReviews = 65,
                    isOpen = true,
                    nextDate = "Hoje, 15h45",
                    location = "Liberdade",
                    distance = "2,0km",
                    paymentsMethods = listOf(PaymentsMethods.CASH, PaymentsMethods.CARD)
                )
            )

            val nextScheduleData =
                NextScheduleData(isScheduled = false, nextScheduleTime = "Hoje, 13h30")

            allEstablishments.value = sampleEstablishments

            _uiState.value = _uiState.value.copy(
                nextSchedule = nextScheduleData,
                isLoading = false
            )
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newQuery)
    }
}
