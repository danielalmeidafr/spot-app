package com.example.spot.data.remote.dtos.details

data class OfferedServicesResponse(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val category: OfferedServiceCategory
)
