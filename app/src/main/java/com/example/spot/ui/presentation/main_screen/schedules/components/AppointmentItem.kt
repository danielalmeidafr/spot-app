package com.example.spot.ui.presentation.main_screen.schedules.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.main_screen.schedules.AppointmentData
import com.student.R

@Composable
fun AppointmentItem(
    appointmentData: AppointmentData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    appointmentData.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(10.dp))

                if (appointmentData.isPaid) {
                    Text(
                        "Pago",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.size(3.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Imagem de check",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(17.dp)
                    )

                } else {
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
                            appointmentData.price ?: "",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground.copy(0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.location),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Location image",
                    modifier = Modifier
                        .size(13.dp)
                        .alpha(0.8f)
                )

                Text(
                    text = appointmentData.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = appointmentData.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = appointmentData.time,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (appointmentData.isPaid) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(85.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        "Avaliar",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 9.sp
                        )
                    )
                }
            } else {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(85.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        "Pagar",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 9.sp
                        )
                    )
                }
            }
        }
    }
}

