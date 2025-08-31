package com.example.spot

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.main_screen.account.AccountScreen
import com.example.spot.ui.presentation.main_screen.calendar.CalendarScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.save.SaveScreen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    val startDestination = Destination.HOME
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                ) {}

                Surface(
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 2.dp,
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(0.95f)
                        .align(Alignment.TopCenter)
                        .offset(y = 10.dp)
                ) {}

                NavigationBar(
                    containerColor = Color.Transparent,
                    windowInsets = NavigationBarDefaults.windowInsets,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Destination.entries.forEachIndexed { index, destination ->
                        val isSelected = selectedDestination == index

                        val offsetY by animateDpAsState(
                            targetValue = if (isSelected) (-3).dp else 0.dp,
                            animationSpec = tween(durationMillis = 300),
                            label = "offsetAnim"
                        )

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (!isSelected) {
                                    navController.navigate(destination.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    selectedDestination = index
                                }
                            },
                            icon = {
                                val iconRes = when {
                                    isSelected && isDarkTheme -> destination.darkIconSelected
                                    isSelected -> destination.lightIconSelected
                                    !isSelected && isDarkTheme -> destination.darkIcon
                                    else -> destination.lightIcon
                                }
                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = destination.label,
                                    modifier = Modifier.size(20.dp).offset(y = offsetY)
                                )
                            },
                            label = {
                                Text(
                                    text = destination.label,
                                    style = MaterialTheme.typography.bodySmall,
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
    ) { contentPadding ->
        AppNavHost(
            navController,
            startDestination,
            modifier = Modifier.padding(
                start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
                top = contentPadding.calculateTopPadding(),
                bottom = 0.dp
            )
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        composable(Destination.HOME.route) { HomeScreen() }
        composable(Destination.SAVE.route) { SaveScreen() }
        composable(Destination.CALENDAR.route) { CalendarScreen() }
        composable(Destination.ACCOUNT.route) { AccountScreen() }
    }
}
