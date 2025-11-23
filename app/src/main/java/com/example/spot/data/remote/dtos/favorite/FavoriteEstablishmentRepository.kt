package com.example.spot.data.remote.dtos.favorite

import com.example.spot.data.remote.network.SpotApiService

class FavoriteEstablishmentRepository(
    private val api: SpotApiService
) {
    suspend fun getAllFavoritesEstablishments(
        customerLatitude: Double? = null,
        customerLongitude: Double? = null
    ) : List<FavoriteEstablishmentResponse>{
        val response = api.getAllFavoritesEstablishments(customerLatitude, customerLongitude)
        return response.establishments
    }
}