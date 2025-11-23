package com.example.spot.ui.presentation.main_screen.favorite.model

import com.example.spot.ui.presentation.main_screen.home.model.EstablishmentData

sealed class FavoriteState {
    data object Loading : FavoriteState()
    data class Error(val message: String) : FavoriteState()
    data class Success(
        val establishmentsData: List<EstablishmentData> = emptyList(),
    ) : FavoriteState()
}
