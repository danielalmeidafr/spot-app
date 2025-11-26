package com.example.spot.ui.presentation.confirm_payment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.confirm_payment.ConfirmPaymentScreen
import com.example.spot.ui.presentation.schedule_service.navigation.ScheduleServiceDestination
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmPaymentDestination(
    val establishmentId: String,
    val offeredServiceId: String
)

fun NavGraphBuilder.confirmPaymentScreen(
    onBack: () -> Unit
) {
    composable<ConfirmPaymentDestination> { backStackEntry ->
        val ids = backStackEntry.toRoute<ScheduleServiceDestination>()

        ConfirmPaymentScreen(
            onBack = onBack,
            establishmentId = ids.establishmentId,
            serviceId = ids.offeredServiceId
        )
    }
}