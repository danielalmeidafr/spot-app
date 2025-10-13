package com.example.spot.ui.presentation.main_screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavGraphBuilder.homeScreen(
    onNavigateToServices: () -> Unit
){
    composable<HomeDestination>{
        HomeScreen(
        //    onNavigateToServices = onNavigateToServices
        )
    }
}
