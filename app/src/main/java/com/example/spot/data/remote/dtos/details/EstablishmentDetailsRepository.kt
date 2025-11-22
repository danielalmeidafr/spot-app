package com.example.spot.data.remote.dtos.details

import com.example.spot.data.remote.network.SpotApiService

class EstablishmentDetailsRepository(
    private val api: SpotApiService
) {
    suspend fun getEstablishmentDetailsById(establishmentId: String): EstablishmentDetailsWrapper{
        return api.getEstablishmentDetailsById(establishmentId)
    }
}