package com.example.spot

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spot.ui.presentation.main_screen.favorite.FavoriteScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
import com.example.spot.ui.presentation.main_screen.schedules.ScheduleScreen
import com.example.spot.ui.theme.SpotTheme
import com.student.R

class BottomAppBarItem(
    @DrawableRes val selectedIconDark: Int,
    @DrawableRes val unselectedIconDark: Int,
    @DrawableRes val selectedIconLight: Int,
    @DrawableRes val unselectedIconLight: Int,
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
fun MainScreen(modifier: Modifier = Modifier) {
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
            Box(
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
                        .offset(y = 10.dp),
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
                                val isDarkTheme = isSystemInDarkTheme()
                                val iconRes = if (isSelected) {
                                    if (isDarkTheme) navItem.bottomAppBarItem.selectedIconDark else navItem.bottomAppBarItem.selectedIconLight
                                } else {
                                    if (isDarkTheme) navItem.bottomAppBarItem.unselectedIconDark else navItem.bottomAppBarItem.unselectedIconLight
                                }

                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = navItem.bottomAppBarItem.label,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    navItem.bottomAppBarItem.label,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.5.sp),
                                    color = MaterialTheme.colorScheme.onBackground,
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
            Modifier.padding(innerPadding)
        ) { page ->
            val item = screens[page]
            when (item) {
                ScreenItem.Home -> HomeScreen()
                ScreenItem.Favorite -> FavoriteScreen()
                ScreenItem.Schedules -> ScheduleScreen()
                ScreenItem.Profile -> ProfileScreen()
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    SpotTheme {
        MainScreen()
    }
}