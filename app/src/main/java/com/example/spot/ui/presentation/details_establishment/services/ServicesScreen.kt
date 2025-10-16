package com.example.spot.ui.presentation.details_establishment.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.ui.presentation.details_establishment.services.components.ServiceItem
import com.student.R


@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    viewModel: ServicesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val imageHeight = 280.dp
    val cornerRadius = 25.dp

    LaunchedEffect(Unit) {
        viewModel.loadServices()
    }

    val categories = uiState.categories

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .align(Alignment.TopStart)
                        .size(40.dp)
                        .zIndex(2f),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .padding(end  = 70.dp, top = 16.dp)
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .zIndex(1f),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        painter = painterResource(id = if (isSystemInDarkTheme()) {
                            R.drawable.share_dark
                        } else {
                            R.drawable.share_light
                        }),
                        contentDescription = "Compartilhar",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .padding(end  = 16.dp, top = 16.dp)
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .zIndex(1f),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        painter = painterResource(id = if (isSystemInDarkTheme()) {
                            R.drawable.favorite_dark
                        } else {
                            R.drawable.favorite_light
                        }),
                        contentDescription = "Favoritar",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.bar_image),
                    contentDescription = "Images of establishment",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Barbearia Corleone",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 10.dp)
                )

                Text(
                    "Av. Brigadeiro Faria Lima, 1425 - Loja B2 - Jardim Paulistano, São Paulo - SP, 01452-002",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )

                categories.forEach { category ->
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 20.dp)
                    )

                    category.services.forEachIndexed { index, service ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            ServiceItem(serviceItem = service)

                            if (index < category.services.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
