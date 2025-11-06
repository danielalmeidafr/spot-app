package com.example.spot.data.dtos.schedules

import com.example.spot.data.network.SpotApiService

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