package com.example.spot.ui.presentation.details_establishment.reviews.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.spot.ui.presentation.details_establishment.reviews.ReviewsScreen
import kotlinx.serialization.Serializable

@Serializable
object ReviewsDestination

fun NavGraphBuilder.reviewsScreen(
    onBack: () -> Unit
) {
    composable<ReviewsDestination> {
        ReviewsScreen(
            onBack = onBack
        )
    }
}