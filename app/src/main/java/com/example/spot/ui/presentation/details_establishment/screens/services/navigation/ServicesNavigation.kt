package com.example.spot.ui.presentation.details_establishment.screens.services.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.details_establishment.screens.details.navigation.DetailsDestination
import com.example.spot.ui.presentation.details_establishment.screens.services.ServicesScreen
import kotlinx.serialization.Serializable

@Serializable
data class ServicesDestination(
    val establishmentId: String
)

fun NavGraphBuilder.servicesScreen(
    onBack: () -> Unit
) {
    composable<ServicesDestination> { backStackEntry ->
        val id = backStackEntry.toRoute<DetailsDestination>()

        ServicesScreen(
            establishmentId = id.establishmentId,
            onBack = onBack
        )
    }
}