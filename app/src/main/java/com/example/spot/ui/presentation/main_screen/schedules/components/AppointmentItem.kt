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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.data.remote.dtos.confirm_payment.Status
import com.example.spot.ui.presentation.main_screen.schedules.model.AppointmentData
import com.student.R

@Composable
fun AppointmentItem(
    appointmentData: AppointmentData,
    onNavigateToReviewEstablishment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isFinished = appointmentData.status == Status.FINISHED

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
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.location),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Location image",
                    modifier = Modifier
                        .size(11.dp)
                        .alpha(0.6f)
                )

                Spacer(modifier = Modifier.size(3.dp))

                Text(
                    text = appointmentData.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = appointmentData.day.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "de",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = appointmentData.month,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = appointmentData.time,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onNavigateToReviewEstablishment(appointmentData.establishmentId) },
                enabled = isFinished,
                modifier = Modifier
                    .width(85.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
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
        }
    }
}