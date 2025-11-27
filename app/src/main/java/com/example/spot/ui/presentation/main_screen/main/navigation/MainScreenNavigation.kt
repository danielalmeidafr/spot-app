package com.example.spot.ui.presentation.main_screen.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.main.MainScreen
import kotlinx.serialization.Serializable


@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToReviewEstablishment: (String) -> Unit,
    onNavigateToSignIn: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    composable<MainScreenDestination> {
        MainScreen(
            onNavigateToDetails = onNavigateToDetails,
            onNavigateToSignIn = onNavigateToSignIn,
            onNavigateToReviewEstablishment = onNavigateToReviewEstablishment,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )
    }
}
