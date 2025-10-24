package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.core.util.rememberKeyboardVisibility
import com.example.spot.ui.presentation.main_screen.home.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCardSkeleton
import com.example.spot.ui.presentation.main_screen.home.components.NextScheduleCard
import com.student.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToServices: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val viewModel = viewModel<HomeViewModel>()
    val uiState by viewModel.state.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()

    when (val state = uiState) {
        HomeState.Loading -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    NextScheduleCard()
                }

                stickyHeader {
                    Text(
                        "Recomendadas:",
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = 10.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                }

                items(5) {
                    EstablishmentCardSkeleton()
                }
            }
        }

        is HomeState.Success -> {
            val isNotFoundScreen = state.searchQuery.isNotBlank() && state.establishments.isEmpty()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .clearFocusOnTap(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomSearchBar(
                    query = state.searchQuery,
                    onQueryChange = { newQuery -> viewModel.updateSearchQuery(newQuery) },
                    modifier = Modifier
                        .padding(top = 20.dp)
                )

                if (isNotFoundScreen) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(bottom = if (isKeyboardVisible) 250.dp else 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.help),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Save Image",
                            modifier = Modifier
                                .size(80.dp)
                                .alpha(0.7f)
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            "Nenhuma barbearia foi encontrada",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            "Verifique se digitou corretamente e tente novamente",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        item {
                            NextScheduleCard(
                                nextScheduleTime = state.nextSchedule.nextScheduleTime
                            )
                        }

                        stickyHeader {
                            Text(
                                text = state.listTitle,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(bottom = 10.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Start
                            )
                        }

                        items(state.establishments) { establishment ->
                            EstablishmentCard(
                                establishmentData = establishment,
                                onNavigateToServices = onNavigateToServices
                            )
                        }
                    }
                }
            }
        }
    }
}