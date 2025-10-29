package com.example.spot.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onNavigateToServices: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    composable<MainScreenDestination> {
        MainScreen(
            onNavigateToServices = onNavigateToServices,
            onNavigateToSignIn = onNavigateToSignIn,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )
    }
}
