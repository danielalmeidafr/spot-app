package com.example.spot.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.details_establishment.services.navigation.ServicesDestination
import com.example.spot.ui.presentation.details_establishment.services.navigation.servicesScreen
import com.example.spot.ui.presentation.auth.login.navigation.LoginDestination
import com.example.spot.ui.presentation.auth.login.navigation.loginScreen
import com.example.spot.ui.presentation.auth.signup.navigation.SignupDestination
import com.example.spot.ui.presentation.auth.signup.navigation.signupScreen
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
            onNavigateToLogin = { navController.navigate(LoginDestination) },
            onNavigateToServices = { navController.navigate(ServicesDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        homeScreen(
            onNavigateToServices = { navController.navigate(ServicesDestination) }
        )

        loginScreen(
            onNavigateToSignup = { navController.navigate(SignupDestination) },
            onBack = { navController.popBackStack() }
        )

        signupScreen(
            onBack = { navController.popBackStack() }
        )

        profileScreen(
            onNavigateToLogin = { navController.navigate(LoginDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        servicesScreen(
            onBack = { navController.popBackStack() }
        )
    }
}