package com.example.spot.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.auth.screens.confirm_code.navigation.ConfirmCodeDestination
import com.example.spot.ui.presentation.auth.screens.confirm_code.navigation.confirmCodeScreen
import com.example.spot.ui.presentation.auth.screens.forgot_password.navigation.ForgotPasswordDestination
import com.example.spot.ui.presentation.auth.screens.forgot_password.navigation.forgotPasswordScreen
import com.example.spot.ui.presentation.auth.screens.new_password.navigation.NewPasswordDestination
import com.example.spot.ui.presentation.auth.screens.new_password.navigation.newPasswordScreen
import com.example.spot.ui.presentation.auth.screens.signin.navigation.SignInDestination
import com.example.spot.ui.presentation.auth.screens.signin.navigation.signInScreen
import com.example.spot.ui.presentation.auth.screens.signup.navigation.SignUpDestination
import com.example.spot.ui.presentation.auth.screens.signup.navigation.signUpScreen
import com.example.spot.ui.presentation.create_profile.navigation.CreateProfileDestination
import com.example.spot.ui.presentation.create_profile.navigation.createProfileScreen
import com.example.spot.ui.presentation.details_establishment.details.navigation.DetailsDestination
import com.example.spot.ui.presentation.details_establishment.details.navigation.detailsScreen
import com.example.spot.ui.presentation.details_establishment.services.navigation.ServicesDestination
import com.example.spot.ui.presentation.details_establishment.services.navigation.servicesScreen
import com.example.spot.ui.presentation.main_screen.home.navigation.homeScreen
import com.example.spot.ui.presentation.main_screen.main.navigation.MainScreenDestination
import com.example.spot.ui.presentation.main_screen.main.navigation.mainScreen
import com.example.spot.ui.presentation.main_screen.profile.navigation.profileScreen

@Composable
fun AppNavHost(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenDestination) {
        detailsScreen()

        mainScreen(
            onNavigateToSignIn = { navController.navigate(SignInDestination) },
            onNavigateToDetails = { navController.navigate(DetailsDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        homeScreen(
            onNavigateToDetails = { navController.navigate(ServicesDestination) }
        )

        signInScreen(
            onBack = { navController.popBackStack() },
            onNavigateToMain = { navController.navigate(MainScreenDestination) },
            onNavigateToSignup = { navController.navigate(SignUpDestination) },
            onNavigateToForgotPassword = { navController.navigate(ForgotPasswordDestination) }
        )

        forgotPasswordScreen(
            onBack = { navController.popBackStack() },
            onNavigateToConfirmCode = { email ->
                navController.navigate(ConfirmCodeDestination(email, false))
            }
        )

        newPasswordScreen(
            onBack = { navController.popBackStack() },
            onNavigateToSignIn = { navController.navigate(SignInDestination) }
        )

        signUpScreen(
            onBack = { navController.popBackStack() },
            onNavigateToConfirmCode = { email ->
                navController.navigate(ConfirmCodeDestination(email, true))
            }
        )

        confirmCodeScreen(
            onBack = { navController.popBackStack() },
            onNavigateToNewPassword = { email, code ->
                navController.navigate(NewPasswordDestination(email, code))
            },
            onNavigateToCreateProfile = { navController.navigate(CreateProfileDestination) }
        )

        createProfileScreen(
            onNavigateToMain = { navController.navigate(MainScreenDestination) }
        )

        profileScreen(
            onNavigateToSignIn = { navController.navigate(SignInDestination) },
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle
        )

        servicesScreen()
    }
}