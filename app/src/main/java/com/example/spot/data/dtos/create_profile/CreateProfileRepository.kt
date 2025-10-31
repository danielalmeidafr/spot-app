package com.example.spot.data.dtos.create_profile

import com.example.spot.data.network.SpotApiService

class CreateProfileRepository(
    private val api: SpotApiService
) {
    suspend fun createProfile(request: CreateProfileRequest){
        return api.createProfile(request)
    }
}