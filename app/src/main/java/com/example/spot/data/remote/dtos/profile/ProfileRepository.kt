package com.example.spot.data.remote.dtos.profile

import com.example.spot.data.remote.network.SpotApiService

class ProfileRepository(
    private val api: SpotApiService
) {

    suspend fun getInfoProfile(): ProfileWrapper{
        return api.getInfoProfile()
    }
}