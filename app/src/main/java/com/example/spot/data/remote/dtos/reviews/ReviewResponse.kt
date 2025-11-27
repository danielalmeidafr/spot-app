package com.example.spot.data.remote.dtos.reviews

data class ReviewResponse(
    val averageScore: Double,
    val totalReviews: Int,
    val reviews: List<Review>
)
