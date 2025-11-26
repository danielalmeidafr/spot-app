package com.example.spot.data.remote.dtos.schedule_service

import com.example.spot.data.remote.network.SpotApiService

class ScheduleServiceRepository(
    private val api: SpotApiService
) {
    suspend fun getInfoAppointment(establishmentId: String, offeredServiceId: String, appointmentDate: String): ScheduleServiceResponse{
        return api.getInfoAppointment(establishmentId, offeredServiceId, appointmentDate)
    }
}