package com.example.spot.ui.presentation.confirm_payment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.confirm_payment.ConfirmPaymentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmPaymentDestination(
    val attendantId: String,
    val establishmentId: String,
    val offeredServiceId: String,
    val scheduleAt: String
)

fun NavGraphBuilder.confirmPaymentScreen(
    onBack: () -> Unit,
    onNavigateToSuccessFullConfirmPayment: () -> Unit,
) {
    composable<ConfirmPaymentDestination> { backStackEntry ->
        val ids = backStackEntry.toRoute<ConfirmPaymentDestination>()

        ConfirmPaymentScreen(
            onBack = onBack,
            onNavigateToSuccessFullConfirmPayment = onNavigateToSuccessFullConfirmPayment,
            attendantId = ids.attendantId,
            establishmentId = ids.establishmentId,
            offeredServiceId = ids.offeredServiceId,
            scheduledAt = ids.scheduleAt
        )
    }
}