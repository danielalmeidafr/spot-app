package com.example.spot.data.network

import com.example.spot.data.dtos.auth.AuthResponse
import com.example.spot.data.dtos.auth.code.ConfirmCodeRequest
import com.example.spot.data.dtos.auth.password.ForgotPasswordRequest
import com.example.spot.data.dtos.auth.LogoutRequest
import com.example.spot.data.dtos.auth.SignInRequest
import com.example.spot.data.dtos.auth.SignUpRequest
import com.example.spot.data.dtos.auth.password.NewPasswordRequest
import com.example.spot.data.dtos.create_profile.CreateProfileRequest
import com.example.spot.data.dtos.home.establishment.PagedEstablishmentsResponse
import com.example.spot.data.dtos.home.nextschedule.NextScheduleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SpotApiService {

    @POST("api/authentication/logout")
    suspend fun logout(@Body request: LogoutRequest)

    @GET("api/establishments")
    suspend fun getAllEstablishments(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortDir") sortDir: String,
        @Query("name") name: String? = null
    ): PagedEstablishmentsResponse

    @POST("api/authentication/signin")
    suspend fun signIn(@Body request: SignInRequest): AuthResponse

    @POST("api/authentication/password-reset/request")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest)

    @POST("api/authentication/password-reset/confirm")
    suspend fun newPassword(@Body request: NewPasswordRequest)

    @POST("api/authentication/signup")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse

    @POST("api/authentication/password-reset/verify")
    suspend fun confirmCode(@Body request: ConfirmCodeRequest)

    @POST("")
    suspend fun createProfile(@Body request: CreateProfileRequest)

    @GET("api/appointments/me/next")
    suspend fun getNextSchedule(): NextScheduleResponse
}