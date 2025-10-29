package com.example.spot.data.network

import com.example.spot.data.dtos.auth.signup.SignupRequest
import com.example.spot.data.dtos.auth.signup.SignupResponse
import com.example.spot.data.dtos.home.establishment.PagedEstablishmentsResponse
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
    suspend fun signup(@Body request: SignupRequest): SignupResponse
}