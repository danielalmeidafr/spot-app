package com.example.spot.data.remote.dtos.home.establishment

data class EstablishmentDto(
    val id: String,
    val name: String,
    val averageScore: Double,
    val totalReviews: Int,
    val isOpen: Boolean,
    val nextDate: String,
    val location: String,
    val distance: String?,
    val paymentMethods: List<String>,
    val banner: String?
)