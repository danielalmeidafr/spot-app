package com.example.spot.ui.presentation.details_establishment.screens.reviews.model

data class ReviewUiModel(
    val userName: String,
    val userImage: String,
    val score: Double,
    val comment: String,
    val formattedDate: String
)