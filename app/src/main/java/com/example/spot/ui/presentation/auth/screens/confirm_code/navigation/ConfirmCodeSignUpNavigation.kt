package com.example.spot.ui.presentation.auth.screens.confirm_code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodePasswordScreen
import com.example.spot.ui.presentation.auth.screens.confirm_code.ConfirmCodeSignUpScreen
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmCodeSignUpDestination(
    val email: String,
    val password: String
)

fun NavGraphBuilder.confirmCodeSingUpScreen(
    onBack: () -> Unit,
    onNavigateToCreateProfile: () -> Unit
) {
    composable<ConfirmCodeSignUpDestination> { backStackEntry ->
        val userEmailAndPassword = backStackEntry.toRoute<ConfirmCodeSignUpDestination>()

        ConfirmCodeSignUpScreen (
            onBack = onBack,
            onNavigateToCreateProfile = onNavigateToCreateProfile,
            email = userEmailAndPassword.email,
            password = userEmailAndPassword.password
        )
    }
}