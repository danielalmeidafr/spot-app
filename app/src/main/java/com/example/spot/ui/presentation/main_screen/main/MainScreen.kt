package com.example.spot.ui.presentation.main_screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.main_screen.favorite.FavoriteScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
import com.example.spot.ui.presentation.main_screen.schedules.ScheduleScreen
import com.student.R

class BottomAppBarItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val label: String
)

sealed class ScreenItem(
    val bottomAppBarItem: BottomAppBarItem
) {
    data object Home : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIcon = R.drawable.home_filled,
            unselectedIcon = R.drawable.home,
            label = "Início"
        )
    )

    data object Favorite : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIcon = R.drawable.favorite_filled,
            unselectedIcon = R.drawable.favorite,
            label = "Favoritos"
        )
    )

    data object Schedules : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIcon = R.drawable.schedule_filled,
            unselectedIcon = R.drawable.schedule,
            label = "Agendamentos"
        )
    )

    data object Profile : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIcon = R.drawable.profile_filled,
            unselectedIcon = R.drawable.profile,
            label = "Perfil"
        )
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToSignIn: () -> Unit,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val screens = remember {
        listOf(
            ScreenItem.Home,
            ScreenItem.Favorite,
            ScreenItem.Schedules,
            ScreenItem.Profile
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
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(400)
                ) + fadeIn(animationSpec = tween(400)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(600)
                ) + fadeOut(animationSpec = tween(600))
            ) {
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
                        .offset(y = 10.dp),
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
                        NavigationBarItem(
                            selected = currentScreen == navItem,
                            onClick = {
                                currentScreen = navItem
                            },
                            icon = {
                                val isSelected = currentScreen == navItem
                                val iconRes = if (isSelected) {
                                    navItem.bottomAppBarItem.selectedIcon
                                } else {
                                    navItem.bottomAppBarItem.unselectedIcon
                                }

                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = navItem.bottomAppBarItem.label,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                            },
                            label = {
                                Text(
                                    navItem.bottomAppBarItem.label,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                            )
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
                ScreenItem.Home -> {
                    HomeScreen(
                        innerPadding = innerPadding,
                        onNavigateToDetails = onNavigateToDetails
                    )
                }

                ScreenItem.Favorite -> FavoriteScreen(innerPadding = innerPadding)
                ScreenItem.Schedules -> ScheduleScreen()
                ScreenItem.Profile -> ProfileScreen(
                    innerPadding = innerPadding,
                    onNavigateToSignIn = onNavigateToSignIn,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        }
    }
}