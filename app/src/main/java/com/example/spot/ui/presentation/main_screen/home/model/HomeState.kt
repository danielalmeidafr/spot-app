package com.example.spot.ui.presentation.main_screen.home.model

sealed class HomeState {
    data object Loading : HomeState()

    data class Success(
        val searchQuery: String = "",
        val nextSchedule: NextScheduleData = NextScheduleData(nextScheduleTime = null),
        val listTitle: String = "",
        val establishments: List<EstablishmentData> = emptyList(),
    ) : HomeState()
}
