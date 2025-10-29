package com.example.spot.data.dtos.auth

import com.example.spot.data.di.SpotApi
import com.example.spot.data.dtos.auth.login.SignInRequest
import com.example.spot.data.dtos.auth.login.SignInResponse
import com.example.spot.data.dtos.auth.signup.SignUpRequest
import com.example.spot.data.dtos.auth.signup.SignUpResponse

class AuthRepository {
    suspend fun signIn(
        request: SignInRequest
    ) : SignInResponse {
        return SpotApi.retrofitService.signIn(request)
    }

    suspend fun signUp(
        request: SignUpRequest
    ) : SignUpResponse {
        return SpotApi.retrofitService.signUp(request)
    }
}