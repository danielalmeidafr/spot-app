package com.example.spot.ui.presentation.schedule_service.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.schedule_service.ScheduleServiceScreen
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleServiceDestination(
    val establishmentId: String,
    val offeredServiceId: String
)

fun NavGraphBuilder.scheduleServiceScreen(
    onBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToConfirmPayment: (String, String) -> Unit
) {
    composable<ScheduleServiceDestination> { backStackEntry ->
        val ids = backStackEntry.toRoute<ScheduleServiceDestination>()

        ScheduleServiceScreen(
            onBack = onBack,
            establishmentId = ids.establishmentId,
            serviceId = ids.offeredServiceId,
            onNavigateToSignIn = onNavigateToSignIn,
            onNavigateToConfirmPayment = onNavigateToConfirmPayment
        )
    }
}