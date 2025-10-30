package com.example.spot.data.dtos.home.establishment

import com.example.spot.data.network.SpotApiService

class EstablishmentRepository(
    private val api: SpotApiService
) {
    suspend fun getAllEstablishments(
        page: Int = 0,
        size: Int = 10,
        sortBy: String = "averageRating",
        sortDir: String = "desc",
        name: String? = null
    ): List<EstablishmentDto> {
        val response = api.getAllEstablishments(page, size, sortBy, sortDir, name)
        return response.establishments
    }
}
