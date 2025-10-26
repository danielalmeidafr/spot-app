package com.example.spot.ui.presentation.details_establishment.services.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.details_establishment.services.components.EstablishmentInfo
import com.example.spot.ui.presentation.details_establishment.services.components.ItemCard
import com.example.spot.ui.presentation.details_establishment.services.model.CategoryData
import com.example.spot.ui.presentation.details_establishment.services.model.EstablishmentInfoData

@Composable
fun BodySection(
    establishmentInfo: EstablishmentInfoData,
    categories: List<CategoryData>,
    cornerRadius: Dp
) {
    Column(
        modifier = Modifier
            .offset(y = (-20).dp)
            .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        EstablishmentInfo(establishmentInfoData = establishmentInfo)

        categories.forEach { category ->
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 20.dp)
            )

            category.services.forEachIndexed { index, service ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ItemCard(itemData = service)

                    if (index < category.services.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}