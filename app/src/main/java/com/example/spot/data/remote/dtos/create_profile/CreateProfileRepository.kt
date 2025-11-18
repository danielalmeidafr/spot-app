package com.example.spot.data.remote.dtos.create_profile

import com.example.spot.data.remote.network.SpotApiService

class CreateProfileRepository(
    private val api: SpotApiService
) {
    suspend fun createProfile(request: CreateProfileRequest){
        return api.createProfile(request)
    }
}