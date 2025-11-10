package com.example.spot.ui.presentation.details_establishment.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.details_establishment.details.DetailsScreen
import com.example.spot.ui.presentation.main_screen.main.MainScreen
import kotlinx.serialization.Serializable


@Serializable
object DetailsDestination

fun NavGraphBuilder.detailsScreen(
    onBack: () -> Unit
) {
    composable<DetailsDestination> {
        DetailsScreen(
            onBack = onBack
        )
    }
}
