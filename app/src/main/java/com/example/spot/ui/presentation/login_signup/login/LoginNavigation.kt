package com.example.spot.ui.presentation.login_signup.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination

fun NavGraphBuilder.loginScreen(
    onNavigateToSignup:() -> Unit
) {
    composable<LoginDestination> {
        LoginScreen(
            onNavigateToSignup = { onNavigateToSignup() },
            onNavigateToWelcome = { }
        )
    }
}