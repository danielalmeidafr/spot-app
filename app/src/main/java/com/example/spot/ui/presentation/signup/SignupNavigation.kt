package com.example.spot.ui.presentation.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SignupDestination

fun NavGraphBuilder.signupScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<SignupDestination> {
        SignupScreen(
            onNavigateToHome = { onNavigateToHome() },
            onNavigateToLogin = { onNavigateToLogin() }
        )
    }
}