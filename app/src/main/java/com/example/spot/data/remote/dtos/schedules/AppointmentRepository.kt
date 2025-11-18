package com.example.spot.data.remote.dtos.schedules

import com.example.spot.data.remote.network.SpotApiService

class AppointmentRepository(
    private val api: SpotApiService
) {
    suspend fun getAllAppointments(
        month: Int
    ): List<AppointmentDto>{
        val response = api.getAllAppointments(month)
        return response.appointments
    }
}