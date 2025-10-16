package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.presentation.main_screen.home.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
import com.example.spot.ui.presentation.main_screen.home.components.NextScheduleCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToServices: () -> Unit,
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val query = uiState.searchQuery

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            query = query,
            onQueryChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .padding(top = 20.dp)
        )

        if (uiState.isLoading){
            LoadingScreen()
        } else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    NextScheduleCard(
                        isScheduled = uiState.nextSchedule.isScheduled,
                        nextSchedule = uiState.nextSchedule.nextScheduleTime
                    )
                }

                stickyHeader {
                    Text(
                        uiState.listTitle,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = 10.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                }

                items(uiState.establishments) { establishment ->
                    EstablishmentCard(
                        name = establishment.name,
                        rating = establishment.rating,
                        totalReviews = establishment.totalReviews,
                        isAvailable = establishment.isAvailable,
                        nextTime = establishment.nextTime,
                        neighborhood = establishment.neighborhood,
                        distance = establishment.distance,
                        paymentsMethods = establishment.paymentsMethods,
                        onNavigateToServices = onNavigateToServices
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Text(
                text = "Carregando informações...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}