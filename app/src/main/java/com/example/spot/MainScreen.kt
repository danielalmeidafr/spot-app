package com.example.spot

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.main_screen.calendar.CalendarScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
import com.example.spot.ui.presentation.main_screen.save.SaveScreen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.CALENDAR
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .offset(y = 45.dp)
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    shadowElevation = 1.dp,
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
                Destination.entries.forEachIndexed { index, destination ->
                    val isSelected = selectedDestination == index

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
                        animationSpec = tween(durationMillis = 300),
                        label = "offsetAnim"
                    )

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (selectedDestination != index) {
                                navController.navigate(destination.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                selectedDestination = index
                            }
                        },
                        icon = {
                            val iconRes = if (isSelected) {
                                if (isDarkTheme) destination.darkIconSelected else destination.lightIconSelected
                            } else {
                                if (isDarkTheme) destination.darkIcon else destination.lightIcon
                            }

                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = destination.label,
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(y = offsetY)
                            )
                        },
                        label = {
                            Text(
                                destination.label,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.5.sp),
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
    ) { contentPadding ->
        AppNavHost(navController, startDestination, contentPadding = contentPadding)
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> HomeScreen(contentPadding = contentPadding)
                    Destination.SAVE -> SaveScreen()
                    Destination.CALENDAR -> CalendarScreen()
                    Destination.PROFILE -> ProfileScreen(contentPadding = contentPadding)
                }
            }
        }
    }
}