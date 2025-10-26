package com.example.spot.ui.presentation.main_screen.profile.components.skeletons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsSectionSkeleton() {
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
    ) {
        repeat(3) {
            StatsCardSkeleton()
        }
    }
}