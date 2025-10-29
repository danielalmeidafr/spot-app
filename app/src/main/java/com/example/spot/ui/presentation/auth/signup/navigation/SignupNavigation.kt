package com.example.spot.ui.presentation.auth.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.signup.SignupScreen
import kotlinx.serialization.Serializable

@Serializable
object SignupDestination

fun NavGraphBuilder.signupScreen(
    onBack: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    composable<SignupDestination> {
        SignupScreen(
            onBack = onBack,
            onNavigateToMain = onNavigateToMain
        )
    }
}