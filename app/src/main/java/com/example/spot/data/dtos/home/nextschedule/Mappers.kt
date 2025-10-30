package com.example.spot.data.dtos.home.nextschedule

import com.example.spot.ui.presentation.main_screen.home.model.NextScheduleData

fun NextScheduleDto.toNextScheduleData(): NextScheduleData {
    return NextScheduleData(
        nextScheduleTime = this.nextSchedule
    )
}