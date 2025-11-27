package com.example.spot.ui.presentation.evaluate.success_evaluate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.evaluate.EvaluateScreen
import com.example.spot.ui.presentation.evaluate.success_evaluate.SuccessEvaluate
import kotlinx.serialization.Serializable

@Serializable
object SuccessEvaluateDestination

fun NavGraphBuilder.successEvaluateScreen(
    onNavigateToMain: () -> Unit
) {
    composable<SuccessEvaluateDestination> {
        SuccessEvaluate(
            onNavigateToMain = onNavigateToMain
        )
    }
}