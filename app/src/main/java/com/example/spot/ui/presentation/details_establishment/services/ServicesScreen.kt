package com.example.spot.ui.presentation.details_establishment.services

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.presentation.details_establishment.services.components.sections.ServicesSection
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceState
import com.example.spot.ui.presentation.details_establishment.services.viewmodel.ServicesViewModel


@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val viewModel = viewModel<ServicesViewModel>()
    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        when (val state = state) {
            ServiceState.Loading -> {

            }
            is ServiceState.Success -> ServicesSection(
                establishmentInfo = state.establishmentInfoData,
                categories = state.servicesData,
                onBack = onBack
            )
        }
    }
}