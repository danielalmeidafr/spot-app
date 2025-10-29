package com.example.spot.ui.presentation.main_screen.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object ProfileDestination

fun NavGraphBuilder.profileScreen(
    onNavigateToSignIn: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    composable<ProfileDestination> {
        ProfileScreen(
            onNavigateToSignIn = onNavigateToSignIn,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )
    }
}