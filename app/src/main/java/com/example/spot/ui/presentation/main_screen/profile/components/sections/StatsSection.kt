package com.example.spot.ui.presentation.main_screen.profile.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.profile.components.StatsCard
import com.example.spot.ui.presentation.main_screen.profile.model.StatsData
import com.student.R


@Composable
fun StatsSection(
    statsData: StatsData
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
    ) {
        StatsCard(
            icon = R.drawable.star,
            quantity = statsData.reviews,
            label = "Avaliações"
        )

        StatsCard(
            icon = R.drawable.schedule,
            quantity = statsData.schedules,
            label = "Agendamentos"
        )

        StatsCard(
            icon = R.drawable.favorite,
            quantity = statsData.favorites,
            label = "Favoritos"
        )
    }
}