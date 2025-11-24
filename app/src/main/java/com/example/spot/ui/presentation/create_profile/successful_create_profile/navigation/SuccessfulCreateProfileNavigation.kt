package com.example.spot.ui.presentation.create_profile.successful_create_profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.create_profile.successful_create_profile.SuccessfulCreateProfile
import kotlinx.serialization.Serializable

@Serializable
object SuccessfulCreateProfileDestination

fun NavGraphBuilder.successfulCreateProfile(
    onNavigateToMain: () -> Unit
){
    composable<SuccessfulCreateProfileDestination>{
        SuccessfulCreateProfile(
            onNavigateToMain = onNavigateToMain
        )
    }
}