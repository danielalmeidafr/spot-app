package com.example.spot.ui.presentation.main_screen.home.model

sealed class HomeState {
    data object Loading : HomeState()

    data class Success(
        val searchQuery: String = "",
        val nextScheduleData: NextScheduleData = NextScheduleData(nextScheduleTime = null),
        val listTitle: String = "",
        val establishmentsData: List<EstablishmentData> = emptyList(),
    ) : HomeState()
}
