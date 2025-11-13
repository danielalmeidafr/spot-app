package com.example.spot.ui.presentation.auth.screens.confirm_code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodePasswordScreen
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodeSignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmCodePasswordDestination(
    val email: String
)

fun NavGraphBuilder.confirmCodePasswordScreen(
    onBack: () -> Unit,
    onNavigateToNewPassword: (String, String) -> Unit
) {
    composable<ConfirmCodePasswordDestination> { backStackEntry ->
        val userEmail = backStackEntry.toRoute<ConfirmCodePasswordDestination>()

        ConfirmCodePasswordScreen (
            onBack = onBack,
            onNavigateToNewPassword = onNavigateToNewPassword,
            email = userEmail.email,
        )
    }
}