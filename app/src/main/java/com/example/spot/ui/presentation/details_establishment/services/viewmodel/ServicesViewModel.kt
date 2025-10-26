package com.example.spot.ui.presentation.details_establishment.services.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spot.ui.presentation.details_establishment.services.model.CategoryData
import com.example.spot.ui.presentation.details_establishment.services.model.EstablishmentInfoData
import com.example.spot.ui.presentation.details_establishment.services.model.ItemData
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicesViewModel : ViewModel() {
    private val _state = MutableStateFlow<ServiceState>(ServiceState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)

            val establishmentInfo = fetchEstablishmentInfo()
            val services = fetchServices()

            _state.update {
                ServiceState.Success(
                    establishmentInfoData = establishmentInfo,
                    servicesData = services
                )
            }
        }
    }

    private fun fetchEstablishmentInfo(): EstablishmentInfoData {
        return EstablishmentInfoData(
            title = "Barbearia Corleone",
            location = "Av. Brigadeiro Faria Lima, 1425 - Loja B2 - Jardim Paulistano, São Paulo - SP, 01452-002"
        )
    }

    private fun fetchServices(): List<CategoryData> {
        return listOf(
            CategoryData(
                title = "Cortes:",
                services = listOf(
                    ItemData(
                        title = "Corte simples",
                        description = "Corte social na tesoura",
                        price = "35,00"
                    ),
                    ItemData(
                        title = "Corte degradê",
                        description = "Corte com degradê na máquina",
                        price = "45,00"
                    )
                )
            ),

            CategoryData(
                title = "Processos:",
                services = listOf(
                    ItemData(
                        title = "Progressiva",
                        description = "Procedimento químico que alisa os fios e tira o friz",
                        price = "120,00"
                    ),
                    ItemData(
                        title = "Luzes",
                        description = "Processo químico que ilumina mechas do cabelo sei la sei la sei la sei la",
                        price = "60,00"
                    )
                )
            ),

            CategoryData(
                title = "Acabamentos:",
                services = listOf(
                    ItemData(
                        title = "Sobrancelha",
                        description = "Limpeza e design das sobrancelhas",
                        price = "10,00"
                    ),
                    ItemData(
                        title = "Barba",
                        description = "Barba completa – alinhamento e finalização",
                        price = "20,00"
                    )
                )
            ),

            CategoryData(
                title = "Processos:",
                services = listOf(
                    ItemData(
                        title = "Progressiva",
                        description = "Procedimento químico que alisa os fios e tira o friz.",
                        price = "120,00"
                    ),
                    ItemData(
                        title = "Luzes",
                        description = "Processo químico que ilumina mechas do cabelo sei la sei la sei la sei la.",
                        price = "60,00"
                    )
                )
            )
        )
    }
}
