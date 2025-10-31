package com.example.spot.ui.presentation.create_profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.create_profile.CreateProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object CreateProfileDestination

fun NavGraphBuilder.createProfileScreen(
    onNavigateToMain: () -> Unit
) {
    composable<CreateProfileDestination> {
        CreateProfileScreen(
            onNavigateToMain = onNavigateToMain
        )
    }
}