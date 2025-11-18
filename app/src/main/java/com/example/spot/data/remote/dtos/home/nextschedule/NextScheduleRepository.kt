package com.example.spot.data.remote.dtos.home.nextschedule

import com.example.spot.data.remote.network.SpotApiService

class NextScheduleRepository(
    private val api: SpotApiService
) {
    suspend fun getNextSchedule(): NextScheduleDto {
        val response = api.getNextSchedule()
        return NextScheduleDto(nextSchedule = response.nextDate)
    }
}