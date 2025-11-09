package com.example.spot.ui.presentation.details_establishment.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spot.ui.presentation.details_establishment.description.DescriptionScreen
import com.example.spot.ui.presentation.details_establishment.reviews.ReviewsScreen
import com.example.spot.ui.presentation.details_establishment.services.ServicesScreen

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
            Surface(
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .offset(y = 40.dp)
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 0.8.dp,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(0.95f)
                ) {}
            }

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
    ) { innerPadding ->
        HorizontalPager(
            pagerState,
            Modifier.padding(innerPadding)
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