package com.example.spot.ui.presentation.details_establishment.screens.reviews.model

sealed class ReviewsState{
    data object Loading: ReviewsState()
    data class Error(val message: String): ReviewsState()
    data class Success(
        val averageScore: Double = 0.0,
        val totalReviews: Int = 0,
        val reviews: List<ReviewUiModel> = emptyList(),
    ): ReviewsState()
}
