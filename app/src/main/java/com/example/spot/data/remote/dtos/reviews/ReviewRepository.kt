package com.example.spot.data.remote.dtos.reviews

import com.example.spot.data.remote.network.SpotApiService

class ReviewRepository(
    private val api: SpotApiService
) {
    suspend fun getReviews(
        sortBy: String = "recent",
        establishmentId: String
    ): ReviewResponse {
        return api.getReviews(establishmentId, sortBy)
    }
}