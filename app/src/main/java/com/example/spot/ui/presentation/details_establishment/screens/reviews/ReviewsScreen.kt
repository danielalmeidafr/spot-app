package com.example.spot.ui.presentation.details_establishment.screens.reviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.presentation.details_establishment.screens.reviews.model.ReviewUiModel
import com.example.spot.ui.presentation.details_establishment.screens.reviews.model.ReviewsState
import com.example.spot.ui.presentation.details_establishment.screens.reviews.viewmodel.ReviewsViewModel
import com.student.R
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun ReviewsScreen(
    establishmentId: String,
    onBack: () -> Unit
) {
    val viewModel = koinViewModel<ReviewsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(establishmentId) {
        viewModel.loadReviews(establishmentId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap()
    ) {
        when (val currentState = state) {
            is ReviewsState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ReviewsState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(currentState.message)
                }
            }
            is ReviewsState.Success -> {
                ReviewsContent(
                    averageScore = currentState.averageScore,
                    totalReviews = currentState.totalReviews,
                    reviews = currentState.reviews
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
            shape = RoundedCornerShape(100.dp),
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

@Composable
fun ReviewsContent(
    averageScore: Double,
    totalReviews: Int,
    reviews: List<ReviewUiModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))

                Text(
                    text = String.format(Locale.US, "%.2f", averageScore),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Image(
                    painter = painterResource(id = R.drawable.rating),
                    contentDescription = "Estrela Principal",
                    modifier = Modifier.size(13.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Avaliação geral",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (i in 5 downTo 1) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "$i",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                HorizontalDivider(
                                    modifier = Modifier.width(100.dp),
                                    thickness = 3.dp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$totalReviews avaliações",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Surface(
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 0.5.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .width(150.dp)
                        .height(35.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Melhor avaliado",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.width(5.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_down),
                                contentDescription = "Filtro",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(25.dp)
                                    .offset(y = 1.dp)
                            )
                        }
                    }
                }
            }
        }

        if (reviews.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                    Text("Nenhuma avaliação ainda.", color = Color.Gray)
                }
            }
        } else {
            items(items = reviews) { review ->
                ReviewItem(review = review)
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}

@Composable
fun ReviewItem(review: ReviewUiModel) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    AsyncImage(
                        model = review.userImage,
                        contentDescription = "Imagem de ${review.userName}",
                        placeholder = painterResource(R.drawable.user_image),
                        error = painterResource(R.drawable.user_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }

                Text(
                    text = review.userName,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(onClick = {}) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = "Configurações",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(7.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val starCount = review.score.toInt().coerceIn(0, 5)
            repeat(starCount) {
                Image(
                    painter = painterResource(R.drawable.rating),
                    contentDescription = null,
                    modifier = Modifier.size(11.dp)
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = review.formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                lineHeight = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable { expanded = !expanded }
        )
    }
}