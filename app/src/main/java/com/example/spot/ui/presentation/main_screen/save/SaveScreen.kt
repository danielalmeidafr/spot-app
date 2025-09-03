package com.example.spot.ui.presentation.main_screen.save

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.student.R

@Composable
fun SaveScreen(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val saveRes = if (isDarkTheme) {
            R.drawable.save_dark
        } else {
            R.drawable.save_light
        }

        Image(
            painter = painterResource(id = saveRes),
            contentDescription = "Save Image",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            alpha = (0.7f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            "Nenhuma barbearia favoritada",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Adicione barbearias aos favoritos para vê-las aqui",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}