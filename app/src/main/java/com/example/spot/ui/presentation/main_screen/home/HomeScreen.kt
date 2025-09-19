package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.home.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
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

        Surface(
            modifier = Modifier
                .width(327.dp)
                .height(45.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            shadowElevation = 1.5.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val isScheduled = false

                if (isScheduled) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Seu próximo horário:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "Ter, 13h30",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Text(
                        "Marque agora seu próximo horário!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Text(
            "Recomendadas:",
            modifier = Modifier.fillMaxWidth(0.95f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp)
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