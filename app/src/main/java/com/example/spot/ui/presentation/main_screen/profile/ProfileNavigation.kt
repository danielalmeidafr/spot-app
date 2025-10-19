package com.example.spot.ui.presentation.main_screen.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ProfileDestination

fun NavGraphBuilder.profileScreen(
    onNavigateToLogin: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    composable<ProfileDestination> {
        ProfileScreen(
            onNavigateToLogin = onNavigateToLogin,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )
    }
}