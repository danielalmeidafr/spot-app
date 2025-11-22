package com.example.spot.ui.presentation.details_establishment.model

import com.example.spot.ui.presentation.details_establishment.screens.services.model.EstablishmentDetailsData
import com.example.spot.ui.presentation.details_establishment.screens.services.model.OfferedServiceCategoryData

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Error(val message: String) : DetailsState()
    data class Success(
        val header: EstablishmentDetailsData,
        val services: List<OfferedServiceCategoryData>
    ) : DetailsState()
}