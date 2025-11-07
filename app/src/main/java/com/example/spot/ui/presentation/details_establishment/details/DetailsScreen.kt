package com.example.spot.ui.presentation.details_establishment.details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.details_establishment.description.DescriptionScreen
import com.example.spot.ui.presentation.details_establishment.reviews.ReviewsScreen
import com.example.spot.ui.presentation.details_establishment.services.ServicesScreen
import kotlinx.coroutines.delay

class BottomAppBarItem(
    val label: String
)

sealed class ScreenItem(
    val bottomAppBarItem: BottomAppBarItem
) {
    data object Services : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            label = "Serviços"
        )
    )

    data object Reviews : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            label = "Avaliações"
        )
    )

    data object Details : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            label = "Detalhes"
        )
    )
}

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier
) {
    val screens = remember {
        listOf(
            ScreenItem.Services,
            ScreenItem.Reviews,
            ScreenItem.Details,
        )
    }
    var currentScreen by remember {
        mutableStateOf(screens.first())
    }
    val pagerState = rememberPagerState {
        screens.size
    }
    LaunchedEffect(currentScreen) {
        pagerState.scrollToPage(screens.indexOf(currentScreen))
    }
    LaunchedEffect(pagerState.targetPage) {
        currentScreen = screens[pagerState.targetPage]
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                windowInsets = NavigationBarDefaults.windowInsets,
                modifier = Modifier.fillMaxWidth()
            ) {
                screens.forEach { navItem ->
                    val isSelected = currentScreen == navItem
                    var shouldLift by remember { mutableStateOf(false) }

                    LaunchedEffect(isSelected) {
                        if (isSelected) {
                            delay(100)
                            shouldLift = true
                        } else {
                            shouldLift = false
                        }
                    }

                    val offsetY by animateDpAsState(
                        targetValue = if (shouldLift) (-3).dp else 0.dp,
                        animationSpec = tween(durationMillis = 350),
                        label = "offsetAnim"
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                currentScreen = navItem
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = navItem.bottomAppBarItem.label,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.offset(y = offsetY)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
            )
        ) { page ->
            val item = screens[page]
            when (item) {
                ScreenItem.Services -> ServicesScreen()
                ScreenItem.Reviews -> ReviewsScreen()
                ScreenItem.Details -> DescriptionScreen()
            }
        }
    }
}