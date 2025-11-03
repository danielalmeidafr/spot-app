package com.example.spot.ui.presentation.auth.screens.new_password.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.new_password.NewPasswordScreen
import com.example.spot.ui.presentation.auth.screens.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object NewPasswordDestination

fun NavGraphBuilder.newPasswordScreen(
    onBack: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    composable<NewPasswordDestination> {
        NewPasswordScreen(
            onBack = onBack,
            onNavigateToSignIn = onNavigateToSignIn
        )
    }
}