package com.example.spot.data.remote.dtos.profile

import com.example.spot.ui.presentation.main_screen.profile.model.InfoData
import com.example.spot.ui.presentation.main_screen.profile.model.ProgressData
import com.example.spot.ui.presentation.main_screen.profile.model.StatsData

fun ProfileResponse.toInfoData(): InfoData {
    return InfoData(
        profileImageUrl = this.profileImageUrl ?: "",
        fullName = this.fullName,
        nickname = this.nickName
    )
}

fun ProfileWrapper.toStatsData(): StatsData{
    return StatsData(
        reviews = this.totalReviews,
        schedules = this.totalAppointments,
        favorites = this.totalFavorites
    )
}

fun ProfileWrapper.toProgressData(): ProgressData{
    return ProgressData(
        currentVisits = this.totalAppointments,
        goalVisits = 5,
        rewardText = "10% OFF no próximo serviço agendado na Barbearia Lauzangeles"
    )
}