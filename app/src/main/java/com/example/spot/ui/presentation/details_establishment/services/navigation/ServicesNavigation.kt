package com.example.spot.ui.presentation.details_establishment.services.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.details_establishment.services.ServicesScreen
import kotlinx.serialization.Serializable

@Serializable
object ServicesDestination

fun NavGraphBuilder.servicesScreen(
) {
    composable<ServicesDestination> {
        ServicesScreen()
    }
}