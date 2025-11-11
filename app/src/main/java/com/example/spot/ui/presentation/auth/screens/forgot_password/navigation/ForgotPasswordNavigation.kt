package com.example.spot.ui.presentation.auth.screens.forgot_password.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.forgot_password.ForgotPasswordScreen
import kotlinx.serialization.Serializable

@Serializable
object ForgotPasswordDestination

fun NavGraphBuilder.forgotPasswordScreen(
    onBack: () -> Unit,
    onNavigateToConfirmCodePassword: (String) -> Unit
) {
    composable<ForgotPasswordDestination> {
        ForgotPasswordScreen(
            onBack = onBack,
            onNavigateToConfirmCodePassword = onNavigateToConfirmCodePassword
        )
    }
}