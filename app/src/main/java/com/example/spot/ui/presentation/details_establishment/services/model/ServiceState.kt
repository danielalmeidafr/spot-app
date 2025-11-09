package com.example.spot.ui.presentation.details_establishment.services.model

sealed class ServiceState {
    data object Loading : ServiceState()

    data class Error(val message: String) : ServiceState()

    data class Success(
        val title: String = "",
        val location: String = "",
        val servicesData: List<ServiceCategoryData> = emptyList()
    ) : ServiceState()
}