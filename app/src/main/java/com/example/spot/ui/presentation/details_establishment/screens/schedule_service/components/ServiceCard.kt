package com.example.spot.ui.presentation.details_establishment.screens.schedule_service.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spot.ui.presentation.details_establishment.screens.schedule_service.model.ServiceInfoData
import com.student.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getFormattedTimeRange(startTime: String?): String {
    if (startTime == null) return "-:-"
    return try {
        val start = LocalTime.parse(startTime)
        val end = start.plusMinutes(30)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        "${start.format(formatter)} - ${end.format(formatter)}"
    } catch (e: Exception) {
        startTime
    }
}

@Composable
fun ServiceCard(
    modifier: Modifier = Modifier,
    serviceInfoData: ServiceInfoData,
    selectedTime: String?,
    nameAttendant: String,
    attendantImage: String?
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(150.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = serviceInfoData.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "R$ ${serviceInfoData.price}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = getFormattedTimeRange(selectedTime),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.5.sp),
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 20.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    if (attendantImage.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.user_image),
                            contentDescription = "Imagem do usuário",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    } else {
                        AsyncImage(
                            model = attendantImage,
                            contentDescription = "Imagem do atendente",
                            error = painterResource(R.drawable.user_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }

                Text(
                    text = nameAttendant,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}