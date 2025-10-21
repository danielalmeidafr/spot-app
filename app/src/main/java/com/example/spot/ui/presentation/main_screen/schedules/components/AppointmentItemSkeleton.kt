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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.spot.core.util.shimmerEffect

@Composable
fun AppointmentItemSkeleton(
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
                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()

                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(
                modifier = Modifier
                    .height(13.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()

            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()

            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .width(85.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .shimmerEffect()
            )
        }
    }
}