package com.example.spot.ui.presentation.details_establishment.screens.reviews.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spot.ui.presentation.details_establishment.screens.details.navigation.DetailsDestination
import com.example.spot.ui.presentation.details_establishment.screens.reviews.ReviewsScreen
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsDestination(
    val establishmentId: String
)

fun NavGraphBuilder.reviewsScreen(
    onBack: () -> Unit
) {
    composable<ReviewsDestination> { backStackEntry ->
        val id = backStackEntry.toRoute<DetailsDestination>()

        ReviewsScreen(
            onBack = onBack,
            establishmentId = id.establishmentId
        )
    }
}