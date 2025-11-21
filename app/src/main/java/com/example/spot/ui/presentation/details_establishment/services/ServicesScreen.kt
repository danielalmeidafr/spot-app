package com.example.spot.ui.presentation.details_establishment.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spot.core.theme.SpotTheme
import com.example.spot.ui.presentation.details_establishment.services.components.Service
import com.example.spot.ui.presentation.details_establishment.services.model.ServiceState
import com.example.spot.ui.presentation.details_establishment.services.viewmodel.ServicesViewModel
import com.student.R

@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val viewModel: ServicesViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var isClicked by remember { mutableStateOf(false) }

    when (val state = state) {
        is ServiceState.Error -> {

        }

        ServiceState.Loading -> {

        }

        is ServiceState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().navigationBarsPadding(),
                    verticalArrangement = Arrangement.Top,
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.establishment_image),
                                contentDescription = "Imagens da barberia",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                    }

                    item {
                        Text(
                            state.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(start = 20.dp, bottom = 10.dp, top = 20.dp)
                        )
                    }

                    item {
                        Text(
                            state.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    items(state.servicesData) { serviceCategoryData ->
                        Text(
                            text = serviceCategoryData.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 25.dp)
                        )

                        serviceCategoryData.services.forEachIndexed { index, service ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Service(serviceData = service)

                                if (index < serviceCategoryData.services.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.fillMaxWidth(0.85f),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(Alignment.TopStart)
                        .padding(start = 20.dp, top = 12.dp)
                        .size(40.dp)
                        .zIndex(2f),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape,
                    onClick = onBack
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Botão de voltar",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp, top = 12.dp)
                        .size(40.dp)
                        .zIndex(2f),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape,
                    onClick = {
                        isClicked = !isClicked
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (!isClicked) R.drawable.favorite else R.drawable.favorite_filled
                        ),
                        contentDescription = "Botão de favoritar",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(11.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    SpotTheme {
        ServicesScreen(
            onBack = {}
        )
    }
}