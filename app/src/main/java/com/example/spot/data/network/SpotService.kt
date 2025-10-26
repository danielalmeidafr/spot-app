package com.example.spot.data.network

import retrofit2.http.GET

interface SpotService {

    @GET("/api/establishments")
    suspend fun getAllEstablishments()
}