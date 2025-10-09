package com.example.spot.ui.presentation.details_establishment.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ServiceItem(
    val title: String,
    val description: String,
    val price: String? = null
)

data class ServiceCategory(
    val title: String,
    val services: List<ServiceItem>
)

data class ServiceUiState(
    val categories: List<ServiceCategory> = emptyList()
)

class ServicesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServiceUiState())

    val uiState: StateFlow<ServiceUiState> = _uiState

    fun loadServices() {
        viewModelScope.launch {
            val sampleCategories = listOf(
                ServiceCategory(
                    title = "Cortes:",
                    services = listOf(
                        ServiceItem(
                            title = "Corte simples",
                            description = "Corte social na tesoura.",
                            price = "35,00"
                        ),
                        ServiceItem(
                            title = "Corte degradê",
                            description = "Corte com degradê na máquina.",
                            price = "45,00"
                        )
                    )
                ),

                ServiceCategory(
                    title = "Processos:",
                    services = listOf(
                        ServiceItem(
                            title = "Progressiva",
                            description = "Procedimento químico que alisa os fios e tira o friz.",
                            price = "120,00"
                        ),
                        ServiceItem(
                            title = "Luzes",
                            description = "Processo químico que ilumina mechas do cabelo sei la sei la sei la sei la.",
                            price = "60,00"
                        )
                    )
                )
            )
            _uiState.value = _uiState.value.copy(
                categories = sampleCategories
            )
        }
    }
}
