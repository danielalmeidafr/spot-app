package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.ScheduleServiceScreen
import kotlinx.serialization.Serializable

@Serializable
object ScheduleServiceDestination

fun NavGraphBuilder.scheduleServiceScreen(
    onBack: () -> Unit
) {
    composable<ScheduleServiceDestination> {
        ScheduleServiceScreen(
            onBack = onBack
        )
    }
}