package com.example.spot.ui.presentation.main_screen.favorite

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.spot.ui.presentation.main_screen.favorite.model.FavoriteState
import com.example.spot.ui.presentation.main_screen.favorite.viewmodel.FavoriteViewModel
import com.example.spot.ui.presentation.main_screen.home.components.EstablishmentCard
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.student.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (String) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val viewModel = koinViewModel<FavoriteViewModel>()
    val state by viewModel.state.collectAsState()
    val isLogged by viewModel.isLoggedIn.collectAsState()
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
                Toast.makeText(
                    context,
                    "Ative o GPS para ver barbearias próximas",
                    Toast.LENGTH_SHORT
                ).show()
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
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
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
        is FavoriteState.Loading -> {

        }

        is FavoriteState.Error -> {

        }

        is FavoriteState.Success -> {
            if (state.establishmentsData.isEmpty() || !isLogged) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.favorite),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Save Image",
                        modifier = Modifier
                            .size(80.dp)
                            .alpha(0.7f)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        "Nenhuma barbearia favoritada",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        "Adicione barbearias aos favoritos para vê-las aqui",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    stickyHeader {
                        Text(
                            "Favoritos:",
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(bottom = 10.dp),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        )
                    }

                    items(state.establishmentsData) { establishment ->
                        EstablishmentCard(
                            establishmentData = establishment,
                            onNavigateToDetails = { onNavigateToDetails(establishment.id) }
                        )
                    }
                }
            }
        }
    }
}