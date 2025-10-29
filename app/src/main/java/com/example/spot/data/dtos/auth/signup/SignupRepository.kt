package com.example.spot.data.dtos.auth.signup

import com.example.spot.data.di.SpotApi

class SignupRepository {
    suspend fun signup(
        request: SignupRequest
    ) : SignupResponse {
        return SpotApi.retrofitService.signup(request)
    }
}