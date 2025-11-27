package com.example.spot.ui.presentation.evaluate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.evaluate.EvaluateScreen
import kotlinx.serialization.Serializable

@Serializable
data class EvaluateDestination(
    val establishmentId: String
)

fun NavGraphBuilder.evaluateScreen(
    onBack: () -> Unit,
    onNavigateToSuccess: () -> Unit
){
    composable<EvaluateDestination>{ backStackEntry ->
        val id = backStackEntry.toRoute<EvaluateDestination>()

        EvaluateScreen(
            onBack = onBack,
            onNavigateToSuccess = onNavigateToSuccess,
            establishmentId = id.establishmentId
        )
    }
}