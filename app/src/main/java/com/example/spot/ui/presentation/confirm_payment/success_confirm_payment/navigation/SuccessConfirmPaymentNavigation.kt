package com.example.spot.ui.presentation.confirm_payment.success_confirm_payment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.confirm_payment.ConfirmPaymentScreen
import com.example.spot.ui.presentation.confirm_payment.success_confirm_payment.SuccessfulConfirmPayment
import kotlinx.serialization.Serializable

@Serializable
data object SuccessConfirmPaymentDestination

fun NavGraphBuilder.successConfirmPaymentScreen(
    onNavigateToMain: () -> Unit,
) {
    composable<SuccessConfirmPaymentDestination> {
        SuccessfulConfirmPayment(
            onNavigateToMain = onNavigateToMain
        )
    }
}