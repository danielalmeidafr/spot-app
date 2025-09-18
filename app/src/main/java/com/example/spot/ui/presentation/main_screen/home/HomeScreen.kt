package com.example.spot.ui.presentation.main_screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.main_screen.home.components.CustomSearchBar
import com.example.spot.ui.presentation.main_screen.home.components.clearFocusOnTap
import com.student.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var query by remember { mutableStateOf("") }

        CustomSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Surface(
            modifier = Modifier
                .width(327.dp)
                .height(45.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(14.dp),
            shadowElevation = 1.5.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val isScheduled = false

                if (isScheduled) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Seu próximo horário:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "Ter, 13h30",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Text(
                        "Marque agora seu próximo horário!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Recomendadas:",
            modifier = Modifier.fillMaxWidth(0.93f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(22.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.2.dp
            ),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .size(width = 350.dp, height = 360.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bar_image),
                    contentDescription = "Bar image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Ale's Stylus",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "4,5",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Image(
                            painter = painterResource(id = R.drawable.rating),
                            contentDescription = "Rating image",
                            modifier = Modifier.size(10.5.dp)
                        )

                        Text(
                            "(125 avaliações)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                val isAvailable: Boolean = false

                if (isAvailable){
                    Text(
                        "Aberto",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                } else {
                    Text(
                        "Fechado",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }



                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        "Próximo horário disponível:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        "Hoje, 14h30",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val isDarkTheme = isSystemInDarkTheme()
                    val locationRes =
                        if (isDarkTheme) R.drawable.location_dark else R.drawable.location_light

                    Image(
                        painter = painterResource(id = locationRes),
                        contentDescription = "Location image",
                        modifier = Modifier
                            .size(10.dp)
                            .alpha(0.8f)
                    )

                    Spacer(modifier = Modifier.size(3.dp))

                    Text(
                        "Santana • 2,6km",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        "Aceito:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Image(
                        painter = painterResource(id = R.drawable.pix),
                        contentDescription = "Pix image",
                        modifier = Modifier.size(15.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.cash),
                        contentDescription = "Cash image",
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}