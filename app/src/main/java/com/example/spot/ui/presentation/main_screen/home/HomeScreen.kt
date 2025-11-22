package com.example.spot.ui.presentation.main_screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.core.util.rememberKeyboardVisibility
import com.example.spot.ui.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCardSkeleton
import com.example.spot.ui.presentation.main_screen.home.components.NextScheduleCard
import com.example.spot.ui.presentation.main_screen.home.components.NextScheduleCardSkeleton
import com.example.spot.ui.presentation.main_screen.home.model.HomeState
import com.example.spot.ui.presentation.main_screen.home.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.student.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: () -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isKeyboardVisible = rememberKeyboardVisibility()
    val context = LocalContext.current

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            if (location != null) {
                viewModel.updateUserLocation(location.latitude, location.longitude)
            } else {
                Toast.makeText(context, "Ative o GPS para ver barbearias próximas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocation || coarseLocation) {
            getCurrentLocation()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    when (val state = state) {
        HomeState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .clearFocusOnTap(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomSearchBar(
                    text = "Procure sua barbearia favorita",
                    query = "",
                    onQueryChange = { newQuery -> viewModel.updateSearchQuery(newQuery) },
                    modifier = Modifier
                        .padding(top = 20.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        NextScheduleCardSkeleton()
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
        }

        is HomeState.Error -> {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .clearFocusOnTap(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomSearchBar(
                        text = "Procure sua barbearia favorita",
                        query = "",
                        onQueryChange = { newQuery -> viewModel.updateSearchQuery(newQuery) },
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(bottom = if (isKeyboardVisible) 250.dp else 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Icon(
                                painter = painterResource(id = R.drawable.signal),
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = "Imagem de sem sinal",
                                modifier = Modifier
                                    .size(60.dp)
                                    .alpha(0.7f)
                            )

                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                state.message,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                "Puxe para atualizar ou verifique sua rede",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }


        is HomeState.Success -> {
            val isNotFoundScreen =
                state.searchQuery.isNotBlank() && state.establishmentsData.isEmpty()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .clearFocusOnTap(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomSearchBar(
                    text = "Procure sua barbearia favorita",
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
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.refresh() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.spacedBy(25.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            item {
                                NextScheduleCard(nextScheduleData = state.nextScheduleData)
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

                            items(state.establishmentsData) { establishment ->
                                EstablishmentCard(
                                    establishmentData = establishment,
                                    onNavigateToDetails = onNavigateToDetails
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}