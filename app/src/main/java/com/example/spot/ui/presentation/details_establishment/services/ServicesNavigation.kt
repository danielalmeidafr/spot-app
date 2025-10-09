package com.example.spot.ui.presentation.details_establishment.services

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ServicesDestination

fun NavGraphBuilder.servicesScreen(
) {
    composable<ServicesDestination> {
        ServicesScreen()
    }
}