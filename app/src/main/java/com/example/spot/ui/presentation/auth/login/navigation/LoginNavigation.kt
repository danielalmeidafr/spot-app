package com.example.spot.ui.presentation.auth.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.login.LoginScreen
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