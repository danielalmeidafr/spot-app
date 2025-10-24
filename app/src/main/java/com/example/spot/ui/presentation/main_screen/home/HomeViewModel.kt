package com.example.spot.ui.presentation.main_screen.home

import androidx.lifecycle.ViewModel
import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NextScheduleData(
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

sealed class HomeState {
    data object Loading : HomeState()
    data class Success(
        val searchQuery: String = "",
        val nextSchedule: NextScheduleData = NextScheduleData(
            nextScheduleTime = null
        ),
        val listTitle: String = "",
        val establishments: List<EstablishmentData> = emptyList(),
    ) : HomeState()
}

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()
    private val allEstablishments = fetchEstablishments()

    init {
        val establishments = fetchEstablishments()
        val initialTitle = if (establishments.isEmpty()) "" else "Recomendadas:"
        val nextSchedule = fetchNextSchedule()

        _state.update {
            HomeState.Success(
                nextSchedule = nextSchedule,
                listTitle = initialTitle,
                establishments = establishments
            )
        }
    }

    private fun fetchEstablishments(): List<EstablishmentData> {
        return listOf(
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
    }

    private fun fetchNextSchedule(): NextScheduleData {
        return NextScheduleData(
            nextScheduleTime = "Hoje, 16h00"
        )
    }

    private fun filterEstablishmentsByName(query: String): List<EstablishmentData> {
        return allEstablishments.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun updateSearchQuery(newQuery: String) {
        _state.update { currentState ->
            if (currentState is HomeState.Success) {

                val newTitle: String
                val updatedEstablishments: List<EstablishmentData>

                if (newQuery.isBlank()) {
                    newTitle = "Recomendadas:"
                    updatedEstablishments = fetchEstablishments()
                } else {
                    newTitle = "Resultados da busca:"
                    updatedEstablishments = filterEstablishmentsByName(newQuery)
                }

                return@update currentState.copy(
                    searchQuery = newQuery,
                    listTitle = newTitle,
                    establishments = updatedEstablishments
                )
            } else {
                currentState
            }
        }
    }
}
