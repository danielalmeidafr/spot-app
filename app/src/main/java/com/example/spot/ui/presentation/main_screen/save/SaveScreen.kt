package com.example.spot.ui.presentation.main_screen.save

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.student.R

@Composable
fun SaveScreen(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()
    val addRes = if (isDarkTheme) R.drawable.add_dark else R.drawable.add_light

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Column(
            verticalArrangement = Arrangement.spacedBy(110.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 40.dp)
        ) {
            repeat(3) {
                Card(
                    onClick = {

                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(width = 300.dp, height = 120.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = addRes),
                            contentDescription = "add barb",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
    }
}
