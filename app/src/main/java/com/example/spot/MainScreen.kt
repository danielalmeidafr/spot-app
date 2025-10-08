package com.example.spot

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.login_signup.login.LoginDestination
import com.example.spot.ui.presentation.login_signup.login.loginScreen
import com.example.spot.ui.presentation.login_signup.signup.SignupDestination
import com.example.spot.ui.presentation.login_signup.signup.signupScreen
import com.example.spot.ui.presentation.main_screen.calendar.CalendarScreen
import com.example.spot.ui.presentation.main_screen.home.HomeScreen
import com.example.spot.ui.presentation.main_screen.profile.ProfileScreen
import com.example.spot.ui.presentation.main_screen.profile.profileScreen
import com.example.spot.ui.presentation.main_screen.save.SaveScreen
import kotlinx.coroutines.delay

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = Destination.HOME
    val isDarkTheme = isSystemInDarkTheme()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showExitDialog by remember { mutableStateOf(false) }

    val onNavigateToLogin: () -> Unit = {
        navController.navigate(LoginDestination) {
            launchSingleTop = true
        }
    }

    val shouldShowBottomBar = currentRoute != null && Destination.entries.any { it.route == currentRoute }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(400)) + fadeIn(animationSpec = tween(400)),
                exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(600)) + fadeOut(animationSpec = tween(600))
            ) {
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
                        Destination.entries.forEach { destination ->
                            val isSelected = currentRoute == destination.route

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
                                    navController.navigate(destination.route) {
                                        launchSingleTop = true
                                        restoreState = true
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
            }
        }
    ) { contentPadding ->
        AppNavHost(
            navController,
            startDestination,
            contentPadding = contentPadding,
            onNavigateToLogin = onNavigateToLogin
        )

        BackHandler(enabled = shouldShowBottomBar) {
            showExitDialog = true
        }

        if (showExitDialog) {
            val activity = (LocalContext.current as? android.app.Activity)
            AlertDialog(
                containerColor = MaterialTheme.colorScheme.background,
                onDismissRequest = { showExitDialog = false },
                title = {
                    Text(
                        "Sair do Spot",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                text = {
                    Text(
                        "Você tem certeza que deseja sair?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                },
                confirmButton = {
                    TextButton(onClick = { activity?.finish() }) {
                        Text(
                            "Sim",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text(
                            "Não",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    }
}

private fun getDestinationIndex(route: String?): Int {
    return Destination.entries.indexOfFirst { it.route == route }
}

private val slideInTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    val targetIndex = getDestinationIndex(targetState.destination.route)
    val initialIndex = getDestinationIndex(initialState.destination.route)

    if (targetIndex != -1 && initialIndex != -1) {
        val direction = if (targetIndex > initialIndex) SlideDirection.Left else SlideDirection.Right

        slideIntoContainer(
            towards = direction,
            animationSpec = tween(400)
        ) + fadeIn(tween(400))
    } else {
        null
    }
}

private val slideOutTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    val targetIndex = getDestinationIndex(targetState.destination.route)
    val initialIndex = getDestinationIndex(initialState.destination.route)

    if (targetIndex != -1 && initialIndex != -1) {
        val direction = if (targetIndex > initialIndex) SlideDirection.Left else SlideDirection.Right

        slideOutOfContainer(
            towards = direction,
            animationSpec = tween(400)
        ) + fadeOut(tween(400))
    } else {
        null
    }
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    NavHost(
        navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(
                destination.route,
                enterTransition = slideInTransition,
                exitTransition = slideOutTransition,
                popEnterTransition = slideInTransition,
                popExitTransition = slideOutTransition
            ) {
                when (destination) {
                    Destination.HOME -> HomeScreen(contentPadding = contentPadding)
                    Destination.SAVE -> SaveScreen()
                    Destination.CALENDAR -> CalendarScreen()
                    Destination.PROFILE -> ProfileScreen(
                        contentPadding = contentPadding,
                        onNavigateToLogin = onNavigateToLogin
                    )
                }
            }
        }

        loginScreen(
            onNavigateToSignup = { navController.navigate(SignupDestination) },
            onBack = { navController.popBackStack() }
        )

        signupScreen(
            onBack = { navController.popBackStack() }
        )

        profileScreen(
            onNavigateToLogin = { navController.navigate(LoginDestination) }
        )
    }
}
