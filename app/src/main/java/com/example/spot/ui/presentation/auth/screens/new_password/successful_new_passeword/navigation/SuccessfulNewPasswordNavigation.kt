package com.example.spot.ui.presentation.auth.screens.new_password.successful_new_passeword.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.auth.screens.new_password.successful_new_passeword.SuccessfulNewPassword
import kotlinx.serialization.Serializable

@Serializable
object SuccessfulNewPasswordDestination

fun NavGraphBuilder.successfulNewPassword(
    onNavigateToSignIn: () -> Unit
){
    composable<SuccessfulNewPasswordDestination>{
        SuccessfulNewPassword(
            onNavigateToSignIn = onNavigateToSignIn
        )
    }
}