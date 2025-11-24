package com.example.spot.data.remote.dtos.create_profile

import com.example.spot.data.remote.network.SpotApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateProfileRepository(
    private val api: SpotApiService
) {
    suspend fun createProfile(data: RequestBody, image: MultipartBody.Part?){
        return api.createProfile(data, image)
    }
}