package com.example.spot.ui.presentation.details_establishment.screens.services.model

data class OfferedServiceData(
    val id: String,
    val title: String,
    val description: String,
    val price: String? = null
)