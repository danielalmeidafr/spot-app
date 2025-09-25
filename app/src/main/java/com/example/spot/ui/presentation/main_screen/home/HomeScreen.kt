package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.spot.ui.presentation.main_screen.home.components.PaymentsMethods
import com.example.spot.ui.util.clearFocusOnTap

data class EstablishmentData(
    val name: String,
    val rating: Double,
    val totalReviews: Int,
    val isAvailable: Boolean,
    val nextTime: String,
    val neighborhood: String,
    val distance: String,
    val paymentsMethods: List<PaymentsMethods>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 20.dp)
        )

        val establishmentList = listOf(
            EstablishmentData(
                name = "Studio Barber Lux",
                rating = 4.8,
                totalReviews = 310,
                isAvailable = true,
                nextTime = "Hoje, 16h00",
                neighborhood = "Moema",
                distance = "3,1km",
                paymentsMethods = listOf(
                    PaymentsMethods.PIX,
                    PaymentsMethods.CASH,
                    PaymentsMethods.CARD
                )
            ),

            EstablishmentData(
                name = "Salão Estilo & Beleza",
                rating = 4.3,
                totalReviews = 78,
                isAvailable = false,
                nextTime = "Amanhã, 10h00",
                neighborhood = "Pinheiros",
                distance = "5,4km",
                paymentsMethods = listOf(PaymentsMethods.PIX, PaymentsMethods.CASH)
            ),

            EstablishmentData(
                name = "Tattoo House SP",
                rating = 4.9,
                totalReviews = 452,
                isAvailable = true,
                nextTime = "Hoje, 18h30",
                neighborhood = "Vila Madalena",
                distance = "4,2km",
                paymentsMethods = listOf(PaymentsMethods.PIX)
            ),

            EstablishmentData(
                name = "Beleza Express",
                rating = 4.1,
                totalReviews = 65,
                isAvailable = true,
                nextTime = "Hoje, 15h45",
                neighborhood = "Liberdade",
                distance = "2,0km",
                paymentsMethods = listOf(PaymentsMethods.CASH, PaymentsMethods.CARD)
            )
        )

        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20 .dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                NextScheduleCard(
                    isScheduled = true,
                    nextSchedule = "Hoje, 13h30"
                )
            }

            stickyHeader {
                Text(
                    "Recomendadas:",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 5.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )
            }

            items(establishmentList) { establishment ->
                EstablishmentCard(
                    name = establishment.name,
                    rating = establishment.rating,
                    totalReviews = establishment.totalReviews,
                    isAvailable = establishment.isAvailable,
                    nextTime = establishment.nextTime,
                    neighborhood = establishment.neighborhood,
                    distance = establishment.distance,
                    paymentsMethods = establishment.paymentsMethods,
                )
            }
        }
    }
}