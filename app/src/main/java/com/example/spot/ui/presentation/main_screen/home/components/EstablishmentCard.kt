package com.example.spot.ui.presentation.main_screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.home.EstablishmentData
import com.student.R
import java.util.Locale

@Composable
fun EstablishmentCard(
    establishmentData: EstablishmentData,
    modifier: Modifier = Modifier,
    onNavigateToServices: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 320.dp else 250.dp,
        animationSpec = tween(durationMillis = 300), label = "cardHeight"
    )

    val imageHeight by animateDpAsState(
        targetValue = if (isExpanded) 180.dp else 150.dp,
        animationSpec = tween(durationMillis = 300), label = "imageHeight"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.5.dp
        ),
        border = BorderStroke(
            width = 0.2.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        onClick = {
            if (isExpanded) {
                onNavigateToServices()
            } else {
                isExpanded = true
            }
        },
        shape = RoundedCornerShape(13.dp),
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(cardHeight)
            .animateContentSize()
            .padding(1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bar_image),
                contentDescription = "Bar image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    establishmentData.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.animateContentSize(tween(durationMillis = 300))
                ) {
                    Text(
                        String.format(Locale("pt", "BR"), "%.1f", establishmentData.averageRating),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Image(
                        painter = painterResource(id = R.drawable.rating),
                        contentDescription = "Rating image",
                        modifier = Modifier.size(10.5.dp)
                    )

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Text(
                            "(${establishmentData.totalReviews})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Text(
                text = if (establishmentData.isOpen) "Aberto" else "Fechado",
                style = MaterialTheme.typography.bodySmall,
                color = if (establishmentData.isOpen) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(7.dp))

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            "Próximo horário disponível:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            establishmentData.nextDate,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                "Aceita:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            establishmentData.paymentsMethods.forEach { method ->
                                when (method) {
                                    PaymentsMethods.PIX -> Image(
                                        painter = painterResource(id = R.drawable.pix),
                                        contentDescription = "Pix image",
                                        modifier = Modifier.size(14.dp)
                                    )

                                    PaymentsMethods.CASH -> Image(
                                        painter = painterResource(id = R.drawable.cash),
                                        contentDescription = "Cash image",
                                        modifier = Modifier.size(14.dp)
                                    )

                                    PaymentsMethods.CARD -> Icon(
                                        painter = painterResource(id = R.drawable.card),
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        contentDescription = "Card image",
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                        contentDescription = "Imagem de localização",
                        modifier = Modifier
                            .size(11.dp)
                    )

                    Spacer(modifier = Modifier.size(1.dp))

                    Text(
                        "${establishmentData.location} • ${establishmentData.distance}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (!isExpanded) {
                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            contentDescription = "Seta para baixo",
                            modifier = Modifier
                                .offset(x = 10.dp)
                        )
                    }
                }

                if (isExpanded) {
                    IconButton(
                        onClick = { isExpanded = false }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_up),
                            tint = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                            contentDescription = "Seta para cima",
                            modifier = Modifier
                                .offset(x = 10.dp)
                        )
                    }
                }
            }
        }
    }
}