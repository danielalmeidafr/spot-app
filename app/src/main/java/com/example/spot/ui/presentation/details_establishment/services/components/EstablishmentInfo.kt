package com.example.spot.ui.presentation.details_establishment.services.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.details_establishment.services.model.EstablishmentInfoData

@Composable
fun EstablishmentInfo(
    establishmentInfoData: EstablishmentInfoData
) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = establishmentInfoData.title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
    )

    Text(
        text = establishmentInfoData.location,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outline
    )
}