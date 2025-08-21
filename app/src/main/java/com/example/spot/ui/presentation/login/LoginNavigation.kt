package com.example.spot.ui.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    composable<LoginDestination> {
        LoginScreen(
            onNavigateToHome = { onNavigateToHome() },
            onNavigateToSignup = { onNavigateToSignup() }
        )
    }
}