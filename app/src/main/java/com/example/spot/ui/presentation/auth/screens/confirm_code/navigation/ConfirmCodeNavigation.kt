package com.example.spot.ui.presentation.auth.screens.confirm_code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodeScreen
import com.example.spot.ui.presentation.auth.screens.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object ConfirmCodeDestination

fun NavGraphBuilder.confirmCodeScreen(
    onBack: () -> Unit,
    onNavigateToCreateProfile: () -> Unit
) {
    composable<ConfirmCodeDestination> {
        ConfirmCodeScreen (
            onBack = onBack,
            onNavigateToCreateProfile = onNavigateToCreateProfile
        )
    }
}