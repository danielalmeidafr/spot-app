package com.example.spot.ui.presentation.main_screen.profile.model

sealed class ProfileState {
    data object Loading : ProfileState()

    data class Success(
        val infoData: InfoData,
        val progressData: ProgressData,
        val statsData: StatsData
    ) : ProfileState()
}
