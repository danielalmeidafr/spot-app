package com.example.spot.data.network

import com.example.spot.data.dtos.auth.AuthRequest
import com.example.spot.data.dtos.auth.AuthResponse
import com.example.spot.data.dtos.create_profile.CreateProfileRequest
import com.example.spot.data.dtos.home.establishment.PagedEstablishmentsResponse
import com.example.spot.data.dtos.home.nextschedule.NextScheduleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SpotApiService {
    @GET("api/establishments")
    suspend fun getAllEstablishments(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortDir") sortDir: String,
        @Query("name") name: String? = null
    ): PagedEstablishmentsResponse

    @POST("api/authentication/signup")
    suspend fun signUp(@Body request: AuthRequest): AuthResponse

    @POST("api/authentication/signin")
    suspend fun signIn(@Body request: AuthRequest): AuthResponse

    @POST("")
    suspend fun createProfile(@Body request: CreateProfileRequest)

    @GET("api/appointments/me/next")
    suspend fun getNextSchedule() : NextScheduleResponse
}