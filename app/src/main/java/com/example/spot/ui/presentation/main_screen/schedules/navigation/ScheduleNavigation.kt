package com.example.spot.ui.presentation.main_screen.schedules.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.schedules.ScheduleScreen
import kotlinx.serialization.Serializable

@Serializable
object ScheduleDestination

fun NavGraphBuilder.scheduleScreen(
    onNavigateToReviewEstablishment: (String) -> Unit
    ) {
    composable<ScheduleDestination> {
        ScheduleScreen(
            onNavigateToReviewEstablishment = onNavigateToReviewEstablishment
        )
    }
}