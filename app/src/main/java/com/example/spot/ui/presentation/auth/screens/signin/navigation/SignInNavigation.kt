package com.example.spot.ui.presentation.auth.screens.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.signin.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
object SignInDestination

fun NavGraphBuilder.signInScreen(
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    composable<SignInDestination> {
        SignInScreen(
            onBack = onBack,
            onNavigateToMain = onNavigateToMain,
            onNavigateToSignup = onNavigateToSignup,
            onNavigateToForgotPassword = onNavigateToForgotPassword
        )
    }
}