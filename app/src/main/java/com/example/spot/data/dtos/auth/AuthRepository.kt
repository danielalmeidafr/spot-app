package com.example.spot.data.dtos.auth

import com.example.spot.data.dtos.auth.code.ConfirmCodePasswordRequest
import com.example.spot.data.dtos.auth.code.ConfirmCodeSignUpRequest
import com.example.spot.data.dtos.auth.password.ForgotPasswordRequest
import com.example.spot.data.dtos.auth.password.NewPasswordRequest
import com.example.spot.data.dtos.auth.sign.SignInRequest
import com.example.spot.data.dtos.auth.sign.SignOutRequest
import com.example.spot.data.dtos.auth.sign.SignUpRequest
import com.example.spot.data.network.SpotApiService

class AuthRepository(
    private val api: SpotApiService,
    private val userPrefs: UserPreferencesRepository
) {
    suspend fun signOut(request: SignOutRequest) {
        return api.signOut(request)
    }

    suspend fun signIn(request: SignInRequest): AuthResponse {
        return api.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest) {
        return api.signUp(request)
    }

    suspend fun confirmCodePassword(request: ConfirmCodePasswordRequest) {
        return api.confirmCodePassword(request)
    }

    suspend fun confirmCodeSignUp(request: ConfirmCodeSignUpRequest): AuthResponse {
        return api.confirmCodeSignUp(request)
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest) {
        return api.forgotPassword(request)
    }

    suspend fun newPassword(request: NewPasswordRequest){
        return api.newPassword(request)
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String){
        userPrefs.saveTokens(accessToken, refreshToken)
    }
}