package com.example.spot.ui.presentation.details_establishment.screens.services.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.spot.core.util.shimmerEffect

@Composable
fun ServiceSkeleton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 25.dp, bottom = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }

        Spacer(modifier = Modifier.width(25.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(5.dp))
                .shimmerEffect()
        )
    }
}

@Composable
fun ServicesScreenSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
                .width(200.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .width(150.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, top = 25.dp)
                    .width(100.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            repeat(3) { index ->
                ServiceSkeleton()

                if (index < 2) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .align(Alignment.CenterHorizontally),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}