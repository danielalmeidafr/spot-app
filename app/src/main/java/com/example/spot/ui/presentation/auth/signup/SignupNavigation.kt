package com.example.spot.ui.presentation.auth.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SignupDestination

fun NavGraphBuilder.signupScreen(
    onBack: () -> Unit
) {
    composable<SignupDestination> {
        SignupScreen(
            onBack = onBack
        )
    }
}