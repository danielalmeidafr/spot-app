package com.example.spot.ui.presentation.auth.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateToSignup: () -> Unit,
    onBack: () -> Unit
) {
    composable<LoginDestination> {
        LoginScreen(
            onNavigateToSignup = onNavigateToSignup,
            onBack = onBack
        )
    }
}