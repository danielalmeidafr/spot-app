package com.example.spot.ui.presentation.auth.screens.new_password.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.auth.screens.new_password.NewPasswordScreen
import kotlinx.serialization.Serializable

@Serializable
data class NewPasswordDestination(
    val email: String,
    val code: String
)

fun NavGraphBuilder.newPasswordScreen(
    onBack: () -> Unit,
    onNavigateToSuccessNewPassword: () -> Unit
) {
    composable<NewPasswordDestination> { backStackEntry ->
        val userEmailAndCode = backStackEntry.toRoute<NewPasswordDestination>()

        NewPasswordScreen(
            onBack = onBack,
            onNavigateToSuccessNewPassword = onNavigateToSuccessNewPassword,
            email = userEmailAndCode.email,
            code = userEmailAndCode.code
        )
    }
}