package com.example.spot.data.dtos.home.nextschedule

import com.example.spot.data.network.SpotApiService

class NextScheduleRepository(
    private val api: SpotApiService
) {
    suspend fun getNextSchedule(): NextScheduleDto {
        val response = api.getNextSchedule()
        return NextScheduleDto(nextSchedule = response.nextDate)
    }
}