package com.example.spot.ui.presentation.main_screen.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
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