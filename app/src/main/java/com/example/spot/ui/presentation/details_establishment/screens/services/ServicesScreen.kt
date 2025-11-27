package com.example.spot.ui.presentation.details_establishment.screens.services

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.spot.ui.presentation.details_establishment.model.DetailsState
import com.example.spot.ui.presentation.details_establishment.screens.services.components.Service
import com.example.spot.ui.presentation.details_establishment.screens.services.components.ServicesScreenSkeleton
import com.example.spot.ui.presentation.details_establishment.viewmodel.DetailsViewModel
import com.student.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    establishmentId: String,
    onBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToScheduleService: (String, String) -> Unit
) {
    val viewModel = koinViewModel<DetailsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(establishmentId) {
        viewModel.loadEstablishment(establishmentId)
    }

    when (val state = state) {
        is DetailsState.Error -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(state.message)
            }
        }

        DetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                ServicesScreenSkeleton()

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
            }
        }

        is DetailsState.Success -> {
            if (state.showLoginDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissLoginDialog() },
                    title = {
                        Text(
                            text = "Faça login",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    text = {
                        Text(
                            text = "Você precisa estar logado para favoritar este estabelecimento. Deseja entrar agora?",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.dismissLoginDialog()
                                onNavigateToSignIn()
                            }
                        ) {
                            Text(
                                text = "Entrar",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { viewModel.dismissLoginDialog() }
                        ) {
                            Text(
                                text = "Cancelar",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.Top,
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            val imageUrls = state.imageUrls

                            val pagerState = rememberPagerState(pageCount = { imageUrls.size })

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxSize()
                            ) { page ->
                                AsyncImage(
                                    model = imageUrls[page],
                                    contentDescription = "Imagem do estabelecimento",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            if (imageUrls.size > 1) {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    repeat(pagerState.pageCount) { iteration ->
                                        val color = if (pagerState.currentPage == iteration) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                        }

                                        Box(
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(color)
                                                .size(8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            state.header.title,
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
                            state.header.location,
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

                    items(state.services) { serviceCategoryData ->
                        Text(
                            text = serviceCategoryData.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 35.dp)
                        )

                        serviceCategoryData.services.forEachIndexed { index, service ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Service(offeredServiceData = service, onClick = {onNavigateToScheduleService(establishmentId, service.id)})

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
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    item{
                        Spacer(modifier = Modifier.height(30.dp))
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
                        if (state.isFavorite) {
                            viewModel.unfavorite(establishmentId)
                        } else {
                            viewModel.favorite(establishmentId)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (!state.isFavorite) R.drawable.favorite else R.drawable.favorite_filled
                        ),
                        contentDescription = "Botão de favoritar",
                        tint = if (!state.isFavorite) MaterialTheme.colorScheme.onBackground else Color(0XFFF9D100),
                        modifier = Modifier.padding(11.dp)
                    )
                }
            }
        }
    }
}