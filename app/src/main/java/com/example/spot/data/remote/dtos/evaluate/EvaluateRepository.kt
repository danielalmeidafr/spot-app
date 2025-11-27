package com.example.spot.data.remote.dtos.evaluate

import com.example.spot.data.remote.network.SpotApiService

class EvaluateRepository(
    private val api: SpotApiService
) {
    suspend fun evaluate(establishmentId: String, request: RatingRequest){
        return api.evaluate(establishmentId, request)
    }
}