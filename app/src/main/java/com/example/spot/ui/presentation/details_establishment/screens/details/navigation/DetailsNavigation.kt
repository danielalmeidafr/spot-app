package com.example.spot.ui.presentation.details_establishment.screens.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.details_establishment.screens.details.DetailsScreen
import kotlinx.serialization.Serializable


@Serializable
data class DetailsDestination(
    val establishmentId: String
)

fun NavGraphBuilder.detailsScreen(
    onBack: () -> Unit
) {
    composable<DetailsDestination> { backStackEntry ->
        val id = backStackEntry.toRoute<DetailsDestination>()

        DetailsScreen(
            establishmentId = id.establishmentId,
            onBack = onBack
        )
    }
}