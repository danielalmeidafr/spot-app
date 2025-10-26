package com.example.spot.ui.presentation.main_screen.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavGraphBuilder.homeScreen(
    onNavigateToServices: () -> Unit
) {
    composable<HomeDestination> {
        HomeScreen(
            onNavigateToServices = onNavigateToServices
        )
    }
}
