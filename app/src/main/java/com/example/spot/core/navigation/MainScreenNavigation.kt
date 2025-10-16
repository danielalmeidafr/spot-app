package com.example.spot.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
object MainScreenDestination

fun NavGraphBuilder.mainScreen(
    onNavigateToServices: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<MainScreenDestination> {
        MainScreen(
            onNavigateToServices = onNavigateToServices,
            onNavigateToLogin = onNavigateToLogin
        )

    }
}
