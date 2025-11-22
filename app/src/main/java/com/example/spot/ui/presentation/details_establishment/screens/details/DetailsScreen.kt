package com.example.spot.ui.presentation.details_establishment.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import com.example.spot.ui.presentation.details_establishment.screens.description.DescriptionScreen
import com.example.spot.ui.presentation.details_establishment.screens.reviews.ReviewsScreen
import com.example.spot.ui.presentation.details_establishment.screens.services.ServicesScreen

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    establishmentId: String,
    onBack: () -> Unit
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

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomAppBar(
                modifier = modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    windowInsets = NavigationBarDefaults.windowInsets,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    screens.forEach { navItem ->
                        val isSelected = currentScreen == navItem

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
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
            )
        ) { page ->
            val item = screens[page]
            when (item) {
                ScreenItem.Services -> ServicesScreen(
                    establishmentId = establishmentId,
                    onBack = onBack
                )

                ScreenItem.Reviews -> ReviewsScreen(
                    onBack = onBack
                )
                ScreenItem.Details -> DescriptionScreen()
            }
        }
    }
}