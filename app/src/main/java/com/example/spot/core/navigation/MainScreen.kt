package com.example.spot.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

class BottomAppBarItem(
    val selectedIconDark: Int,
    val unselectedIconDark: Int,
    val selectedIconLight: Int,
    val unselectedIconLight: Int,
    val label: String
)

sealed class ScreenItem(
    val bottomAppBarItem: BottomAppBarItem
) {
    data object Home : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIconDark = R.drawable.home_filled_dark,
            unselectedIconDark = R.drawable.home_dark,
            selectedIconLight = R.drawable.home_filled_light,
            unselectedIconLight = R.drawable.home_light,
            label = "Início"
        )
    )

    data object Favorite : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIconDark = R.drawable.favorite_filled_dark,
            unselectedIconDark = R.drawable.favorite_dark,
            selectedIconLight = R.drawable.favorite_filled_light,
            unselectedIconLight = R.drawable.favorite_light,
            label = "Favoritos"
        )
    )

    data object Schedules : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIconDark = R.drawable.schedule_filled_dark,
            unselectedIconDark = R.drawable.schedule_dark,
            selectedIconLight = R.drawable.schedule_filled_light,
            unselectedIconLight = R.drawable.schedule_light,
            label = "Agendamentos"
        )
    )

    data object Profile : ScreenItem(
        bottomAppBarItem = BottomAppBarItem(
            selectedIconDark = R.drawable.profile_filled_dark,
            unselectedIconDark = R.drawable.profile_dark,
            selectedIconLight = R.drawable.profile_filled_light,
            unselectedIconLight = R.drawable.profile_light,
            label = "Perfil"
        )
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToServices: () -> Unit,
    onNavigateToLogin: () -> Unit
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
                /*Box(
                    modifier = Modifier.fillMaxWidth()
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
                            .offset(y = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            shadowElevation = 0.5.dp,
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            modifier = Modifier
                                .height(55.dp)
                                .fillMaxWidth(0.95f)
                        ) {}
                    }*/
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
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

                        NavigationBarItem(
                            selected = currentScreen == navItem,
                            onClick = {
                                currentScreen = navItem
                            },
                            icon = {
                                val isSelected = currentScreen == navItem
                                val isDarkTheme = isSystemInDarkTheme()
                                val iconRes = if (isSelected) {
                                    if (isDarkTheme) {
                                        navItem.bottomAppBarItem.selectedIconDark
                                    } else {
                                        navItem.bottomAppBarItem.selectedIconLight
                                    }
                                } else if (isDarkTheme) {
                                    navItem.bottomAppBarItem.unselectedIconDark
                                } else {
                                    navItem.bottomAppBarItem.unselectedIconLight
                                }

                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = navItem.bottomAppBarItem.label,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .offset(y = offsetY)
                                )
                            },
                            label = {
                                Text(
                                    navItem.bottomAppBarItem.label,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.offset(y = offsetY)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                unselectedTextColor = MaterialTheme.colorScheme.onBackground,
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
                ScreenItem.Home -> HomeScreen(
                    innerPadding = innerPadding,
                    onNavigateToServices = onNavigateToServices
                )

                ScreenItem.Favorite -> FavoriteScreen(innerPadding = innerPadding)
                ScreenItem.Schedules -> ScheduleScreen()
                ScreenItem.Profile -> ProfileScreen(
                    innerPadding = innerPadding,
                    onNavigateToLogin = onNavigateToLogin
                )
            }
        }
    }
}