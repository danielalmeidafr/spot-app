package com.example.spot.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.details_establishment.services.navigation.ServicesDestination
import com.example.spot.ui.presentation.details_establishment.services.navigation.servicesScreen
import com.example.spot.ui.presentation.auth.signin.navigation.SignInDestination
import com.example.spot.ui.presentation.auth.signin.navigation.signInScreen
import com.example.spot.ui.presentation.auth.signup.navigation.SignUpDestination
import com.example.spot.ui.presentation.auth.signup.navigation.signUpScreen
import com.example.spot.ui.presentation.main_screen.home.navigation.homeScreen
import com.example.spot.ui.presentation.main_screen.profile.navigation.profileScreen

@Composable
fun AppNavHost(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenDestination){
        mainScreen(
            onNavigateToSignIn = { navController.navigate(SignInDestination) },
            onNavigateToServices = { navController.navigate(ServicesDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        homeScreen(
            onNavigateToServices = { navController.navigate(ServicesDestination) }
        )

        signInScreen(
            onBack = { navController.popBackStack() },
            onNavigateToMain = { navController.navigate(MainScreenDestination) },
            onNavigateToSignup = { navController.navigate(SignUpDestination) },
        )

        signUpScreen(
            onBack = { navController.popBackStack() },
            onNavigateToMain = { navController.navigate(MainScreenDestination) }
        )

        profileScreen(
            onNavigateToSignIn = { navController.navigate(SignInDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        servicesScreen(
            onBack = { navController.popBackStack() }
        )
    }
}