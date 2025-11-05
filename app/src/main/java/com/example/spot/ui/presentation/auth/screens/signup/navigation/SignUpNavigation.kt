package com.example.spot.ui.presentation.auth.screens.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object SignUpDestination

fun NavGraphBuilder.signUpScreen(
    onBack: () -> Unit,
    onNavigateToConfirmCode: (String) -> Unit
) {
    composable<SignUpDestination> {
        SignUpScreen(
            onBack = onBack,
            onNavigateToConfirmCode = onNavigateToConfirmCode
        )
    }
}