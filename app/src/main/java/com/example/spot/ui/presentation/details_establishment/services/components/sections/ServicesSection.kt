package com.example.spot.ui.presentation.details_establishment.services.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.details_establishment.services.model.CategoryData
import com.example.spot.ui.presentation.details_establishment.services.model.EstablishmentInfoData

@Composable
fun ServicesSection(
    establishmentInfo: EstablishmentInfoData,
    categories: List<CategoryData>,
    onBack: () -> Unit
) {
    val imageHeight = 280.dp
    val cornerRadius = 25.dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item { HeaderImage(imageHeight) }
        item { BodySection(establishmentInfo, categories, cornerRadius) }
    }

    TopBar(onBack)
    TopBarActions()
}