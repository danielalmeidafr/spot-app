package com.example.spot.ui.presentation.details_establishment.reviews

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.spot.core.theme.SpotTheme
import com.example.spot.core.util.clearFocusOnTap
import com.example.spot.ui.components.CustomSearchBar
import com.student.R

@Composable
fun ReviewsScreen(
    modifier: Modifier = Modifier, onBack: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clearFocusOnTap()
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
                        text = "4.97",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 50.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Image(
                        painter = painterResource(id = R.drawable.rating),
                        contentDescription = "Avaliações",
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
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
                        "6 avaliações",
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "Melhor avaliado",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(Modifier.width(5.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_down),
                                    contentDescription = "FIltro",
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

            item {
                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
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
                                Image(
                                    painter = painterResource(R.drawable.user_image),
                                    contentDescription = "Imagem do usuário",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.matchParentSize()
                                )
                            }

                            Text(
                                text = "Daniel",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }


                        IconButton(
                            onClick = {},
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.settings),
                                    contentDescription = "Configurações do comentário",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) {
                            Image(
                                painter = painterResource(R.drawable.rating),
                                contentDescription = null,
                                modifier = Modifier.size(11.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(7.dp))

                        Text(
                            "15/11/2025",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Corte exatamente como eu queria! Atendimento rápido, profissional e ambiente agradável. Recomendo!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = if (expanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable {
                                expanded = !expanded
                            }
                    )
                }
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

@Preview(showBackground = true)
@Composable
fun ReviewsScreenPreview() {
    SpotTheme {
        ReviewsScreen(
            onBack = {})
    }
}