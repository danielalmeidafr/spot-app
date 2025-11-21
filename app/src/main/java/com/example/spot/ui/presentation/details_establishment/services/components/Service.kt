package com.example.spot.ui.presentation.details_establishment.services.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceData

@Composable
fun Service(
    serviceData: ServiceData,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var hasVisualOverflow by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(start = 30.dp, end = 30.dp, top = 25.dp, bottom = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = serviceData.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(10.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "R$",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            ),
                        color = MaterialTheme.colorScheme.onBackground.copy(0.9f),
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = serviceData.price ?: "",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            val textModifier = if (hasVisualOverflow || isExpanded) {
                Modifier.clickable { isExpanded = !isExpanded }
            } else {
                Modifier
            }

            Text(
                text = serviceData.description,
                modifier = textModifier,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    hasVisualOverflow = textLayoutResult.hasVisualOverflow
                }
            )

            if (hasVisualOverflow && !isExpanded) {
                Text(
                    text = "···",
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                        .clickable { isExpanded = true },
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.width(25.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .width(80.dp)
                    .height(28.dp),
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(horizontal = 1.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Agendar",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 9.sp
                    )
                )
            }
        }
    }
}

