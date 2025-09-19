package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.home.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
import com.example.spot.ui.presentation.main_screen.home.components.NextScheduleCard
import com.example.spot.ui.util.clearFocusOnTap

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var query by remember { mutableStateOf("") }

        CustomSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(23.dp))

        NextScheduleCard(
            isScheduled = true,
            nextSchedule = "Hoje, 13h30"
        )

        Spacer(modifier = Modifier.height(23.dp))

        Text(
            "Recomendadas:",
            modifier = Modifier.fillMaxWidth(0.9f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                EstablishmentCard(
                    name = "Ale's Stylus",
                    rating = 4.5,
                    totalReviews = 125,
                    isAvailable = false,
                    nextTime = "Hoje, 14h30",
                    neighborhood = "Santana",
                    distance = "2,6km"
                )
            }

            item {
                EstablishmentCard(
                    name = "Barbearia do João",
                    rating = 4.2,
                    totalReviews = 90,
                    isAvailable = true,
                    nextTime = "Hoje, 15h00",
                    neighborhood = "Tatuapé",
                    distance = "1,8km"
                )
            }

            item {
                EstablishmentCard(
                    name = "Ale's Stylus",
                    rating = 4.5,
                    totalReviews = 125,
                    isAvailable = false,
                    nextTime = "Hoje, 14h30",
                    neighborhood = "Santana",
                    distance = "2,6km"
                )
            }

            item {
                EstablishmentCard(
                    name = "Barbearia do João",
                    rating = 4.2,
                    totalReviews = 90,
                    isAvailable = true,
                    nextTime = "Hoje, 15h00",
                    neighborhood = "Tatuapé",
                    distance = "1,8km"
                )
            }
        }
    }
}