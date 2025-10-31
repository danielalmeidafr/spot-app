package com.example.spot.ui.presentation.auth.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object SignUpDestination

fun NavGraphBuilder.signUpScreen(
    onBack: () -> Unit,
    onNavigateToCreateProfile: () -> Unit
) {
    composable<SignUpDestination> {
        SignUpScreen(
            onBack = onBack,
            onNavigateToCreateProfile = onNavigateToCreateProfile
        )
    }
}