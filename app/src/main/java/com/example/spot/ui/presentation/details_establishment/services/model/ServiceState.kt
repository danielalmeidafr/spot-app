package com.example.spot.ui.presentation.details_establishment.services.model

sealed class ServiceState {
    data object Loading : ServiceState()

    data class Success(
        val establishmentInfoData: EstablishmentInfoData,
        val servicesData: List<CategoryData> = emptyList()
    ) : ServiceState()
}