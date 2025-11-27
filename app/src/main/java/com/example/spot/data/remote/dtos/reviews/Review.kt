package com.example.spot.data.remote.dtos.reviews

data class Review(
    val customer: Customer,
    val score: Double,
    val comment: String,
    val reviewedAt: String
)
