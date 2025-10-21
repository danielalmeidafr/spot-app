package com.example.spot.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.details_establishment.services.ServicesDestination
import com.example.spot.ui.presentation.details_establishment.services.servicesScreen
import com.example.spot.ui.presentation.auth.login.LoginDestination
import com.example.spot.ui.presentation.auth.login.loginScreen
import com.example.spot.ui.presentation.auth.signup.SignupDestination
import com.example.spot.ui.presentation.auth.signup.signupScreen
import com.example.spot.ui.presentation.main_screen.home.homeScreen
import com.example.spot.ui.presentation.main_screen.profile.profileScreen

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