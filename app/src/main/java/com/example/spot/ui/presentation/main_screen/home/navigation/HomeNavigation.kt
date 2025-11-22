package com.example.spot.ui.presentation.main_screen.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavGraphBuilder.homeScreen(
    onNavigateToDetails: (String) -> Unit
) {
    composable<HomeDestination> {
        HomeScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}
