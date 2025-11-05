package com.example.spot.ui.presentation.auth.screens.confirm_code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodeScreen
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmCodeDestination(
    val email: String
)

fun NavGraphBuilder.confirmCodeScreen(
    onBack: () -> Unit,
    onNavigateToNewPassword: (String, String) -> Unit
) {
    composable<ConfirmCodeDestination> { backStackEntry ->
        val userEmail = backStackEntry.toRoute<ConfirmCodeDestination>()

        ConfirmCodeScreen (
            onBack = onBack,
            onNavigateToNewPassword = onNavigateToNewPassword,
            email = userEmail.email
        )
    }
}