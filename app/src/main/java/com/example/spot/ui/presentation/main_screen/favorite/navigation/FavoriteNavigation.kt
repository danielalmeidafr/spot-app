package com.example.spot.ui.presentation.main_screen.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.main_screen.favorite.FavoriteScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object FavoriteDestination

fun NavGraphBuilder.favoriteScreen(
    onNavigateToDetails: (String) -> Unit
) {
    composable<FavoriteDestination> {
        FavoriteScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}
