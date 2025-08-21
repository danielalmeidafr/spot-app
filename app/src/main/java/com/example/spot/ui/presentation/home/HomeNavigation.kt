package com.example.spot.ui.presentation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavGraphBuilder.homeScreen(){

    composable<HomeDestination> {
        HomeScreen()
    }
}