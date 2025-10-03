package com.example.spot.ui.presentation.login_signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.spot.ui.presentation.login_signup.login.LoginDestination
import com.example.spot.ui.presentation.login_signup.login.loginScreen
import com.example.spot.ui.presentation.login_signup.signup.SignupDestination
import com.example.spot.ui.presentation.login_signup.signup.signupScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = Modifier.fillMaxSize()
    ){
        loginScreen(
            onNavigateToSignup = { navController.navigate(SignupDestination) }
        )

        signupScreen(
            onNavigateToLogin = { navController.navigate(LoginDestination) }
        )
    }
}