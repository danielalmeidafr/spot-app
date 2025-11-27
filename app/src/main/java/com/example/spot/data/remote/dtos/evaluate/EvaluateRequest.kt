package com.example.spot.data.remote.dtos.evaluate

data class RatingRequest(
    val rating: RatingData
)

data class RatingData(
    val score: Int,
    val comment: String
)
